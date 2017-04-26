package net.moltendorf.bukkit.intellidoors.settings

import net.moltendorf.bukkit.intellidoors.*
import org.bukkit.*
import org.bukkit.configuration.*
import java.util.*

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class GlobalSettings {
  private val doors = HashMap<Material, Variations>()
  private val names = HashMap<String, Variations>()

  var enabled = true // Whether or not the plugin is enabled at all; interface mode.
  var interact = false
  var redstone = false
  var version = 0

  init {
    // Make sure the default configuration is saved.
    instance.saveDefaultConfig()

    enabled = config.getBoolean("enabled", enabled)
    version = config.getInt("version", version)

    val doorSub = config.getConfigurationSection("doors")

    if (doorSub != null) {
      for (key in doorSub.getKeys(false)) {
        val typeSub = doorSub.getConfigurationSection(key)

        if (typeSub == null) {
          w { "Config: door.$key is not specified" }
          continue
        }

        val typeString = typeSub.getString("type")

        if (typeString == null) {
          w { "Config: door.$key.type has no value" }
          continue
        }

        val type : Variations.Type

        try {
          type = Variations.Type.valueOf(typeString.toUpperCase())
        } catch (t : Throwable) {
          w { "Config: door.$key.type has invalid value: $typeString" }
          continue
        }

        val materialStrings = typeSub.getStringList("material")

        if (materialStrings.size == 0) {
          w { "Config: door.$key.material has no values" }
          continue
        }

        val materials = ArrayList<Material>()

        for (materialString in materialStrings) {
          val material = Material.getMaterial(materialString)

          if (material == null) {
            w { "Config: door.$key.material has invalid value: $materialString" }
            continue
          }

          if (doors.containsKey(material)) {
            w { "Config: door.$key.material has in use value: $materialString" }
            continue
          }

          materials.add(material)
        }

        if (materials.size == 0) {
          w { "Config: door.$key.material only contains invalid values" }
          continue
        }

        val optional = type == Variations.Type.GATE

        val settings = Variations(
          type,

          // Single
          Settings(
            "single.interact.enabled".getBoolean(typeSub, true),
            "single.interact.reset.enabled".getBoolean(typeSub, true),
            "single.interact.reset.ticks".getLong(typeSub, 100),

            "single.redstone.enabled".getBoolean(typeSub, true),
            "single.redstone.reset.enabled".getBoolean(typeSub, true),
            "single.redstone.reset.ticks".getLong(typeSub, 20)
          ),

          // Pairs
          Settings(
            "pair.interact.enabled".getBoolean(typeSub, true, optional) &&
              "pair.interact.sync".getBoolean(typeSub, true, optional),
            "pair.interact.reset.enabled".getBoolean(typeSub, true, optional),
            "pair.interact.reset.ticks".getLong(typeSub, 100, optional),

            "pair.redstone.enabled".getBoolean(typeSub, true, optional) &&
              "pair.redstone.sync".getBoolean(typeSub, true, optional),
            "pair.redstone.reset.enabled".getBoolean(typeSub, true, optional),
            "pair.redstone.reset.ticks".getLong(typeSub, 20, optional)
          )
        )

        if (settings.single.interact || settings.pair.interact) {
          interact = true // Ensure interact listeners get enabled.
        }

        if (settings.single.redstone || settings.pair.redstone) {
          redstone = true // Ensure redstone listeners get enabled.
        }

        names[key] = settings

        for (material in materials) {
          doors[material] = settings
        }
      }
    }
  }

  operator fun get(material : Material) : Variations? {
    return doors[material]
  }

  operator fun get(name : String) : Variations? {
    return names[name]
  }

  private fun String.getBoolean(sub : ConfigurationSection, default : Bool, optional : Bool = false) : Bool {
    if (sub.contains(this)) {
      if (sub.isBoolean(this)) {
        return sub.getBoolean(this)
      }

      if (sub.isString(this)) {
        val path = sub.getString(this)

        if (config.contains(path)) {
          if (config.isBoolean(path)) {
            return config.getBoolean(path, default)
          } else {
            w { "Config: ${sub.currentPath}.$this has invalid value; $path has incompatible value: ${config.get(path)}" }
          }
        } else {
          w { "Config: ${sub.currentPath}.$this has invalid value; $path has no value" }
        }
      } else {
        w { "Config: ${sub.currentPath}.$this has invalid value: ${sub.get(this)}" }
      }
    }

    if (!optional) {
      w { "Config: ${sub.currentPath}.$this has no value" }
    }

    if (config.contains(this) && config.isBoolean(this)) {
      return config.getBoolean(this, default)
    }

    return default
  }

  private fun String.getLong(sub : ConfigurationSection, default : Long, optional : Bool = false) : Long {
    if (sub.contains(this)) {
      if (sub.isInt(this) || sub.isLong(this)) {
        return sub.getLong(this)
      }

      if (sub.isString(this)) {
        val path = sub.getString(this)

        if (config.contains(path)) {
          if (config.isInt(path) || config.isLong(path)) {
            return config.getLong(path, default)
          } else {
            w { "Config: ${sub.currentPath}.$this has invalid value; $path has incompatible value: ${config.get(path)}" }
          }
        } else {
          w { "Config: ${sub.currentPath}.$this has invalid value; $path has no value" }
        }
      } else {
        w { "Config: ${sub.currentPath}.$this has invalid value: ${sub.get(this)}" }
      }
    }

    if (!optional) {
      w { "Config: ${sub.currentPath}.$this has no value" }
    }

    if (config.contains(this) && (config.isInt(this) || config.isLong(this))) {
      return config.getLong(this, default)
    }

    return default
  }
}
