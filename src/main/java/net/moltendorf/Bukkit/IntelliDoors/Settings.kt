package net.moltendorf.Bukkit.IntelliDoors

import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import java.util.*

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class Settings {
  var enabled = true // Whether or not the plugin is enabled at all; interface mode.

  private val doors = HashMap<Material, TypeSettings>()

  private val instance = IntelliDoors.getInstance()
  private val config = instance.config
  private val log = instance.logger

  init {
    // Make sure the default configuration is saved.
    instance.saveDefaultConfig()

    enabled = config.getBoolean("enabled", enabled)

    val doorSub = config.getConfigurationSection("doors")

    if (doorSub != null) {
      for (key in doorSub.getKeys(false)) {
        val typeSub = doorSub.getConfigurationSection(key)

        if (typeSub == null) {
          log.warning("Config: door.$key is not specified")
          continue
        }

        val typeString = typeSub.getString("type")

        if (typeString == null) {
          log.warning("Config: door.$key.type has no value")
          continue
        }

        val type: Type

        try {
          type = Type.valueOf(typeString.toUpperCase())
        } catch (t: Throwable) {
          log.warning("Config: door.$key.type has invalid value: $typeString")
          continue
        }

        val materials = typeSub.getStringList("material")

        if (materials.size == 0) {
          log.warning("Config: door.$key.material has no values")
          continue
        }

        val settings = TypeSettings(
          type,

          // Single
          "single.interact.enabled".getBoolean(typeSub, true),
          "single.interact.reset.enabled".getBoolean(typeSub, true),
          "single.interact.reset.ticks".getInt(typeSub, 100),

          "single.redstone.enabled".getBoolean(typeSub, true),
          "single.redstone.reset.enabled".getBoolean(typeSub, true),
          "single.redstone.reset.ticks".getInt(typeSub, 20),

          // Pairs
          "pair.interact.enabled".getBoolean(typeSub, true),
          "pair.interact.sync".getBoolean(typeSub, true),
          "pair.interact.reset.enabled".getBoolean(typeSub, true),
          "pair.interact.reset.ticks".getInt(typeSub, 100),

          "pair.redstone.enabled".getBoolean(typeSub, true),
          "pair.redstone.sync".getBoolean(typeSub, true),
          "pair.redstone.reset.enabled".getBoolean(typeSub, true),
          "pair.redstone.reset.ticks".getInt(typeSub, 20)
        );

        for (materialString in materials) {
          val material = Material.getMaterial(materialString)

          if (material == null) {
            log.warning("Config: door.$key.material has invalid value: $materialString")
            continue
          }

          if (doors.containsKey(material)) {
            log.warning("Config: door.$key.material has in use value: $materialString")
            continue
          }

          doors[material] = settings
        }
      }
    }
  }

  operator fun get(material: Material): TypeSettings? {
    return doors[material]
  }

  private fun String.getBoolean(sub: ConfigurationSection, default: Boolean): Boolean {
    if (sub.contains(this)) {
      if (sub.isBoolean(this)) {
        return sub.getBoolean(this, default)
      }

      log.warning("Config: ${sub.currentPath}.$this has invalid value: ${sub.get(this)}")
    }

    if (config.contains(this) && config.isBoolean(this)) {
      return config.getBoolean(this, default)
    }

    log.warning("Config: ${sub.currentPath}.$this has no value")
    return default
  }

  private fun String.getInt(sub: ConfigurationSection, default: Int): Int {
    if (sub.contains(this)) {
      if (sub.isInt(this)) {
        return sub.getInt(this, default)
      }

      log.warning("Config: ${sub.currentPath}.$this has invalid value: ${sub.get(this)}")
    }

    if (config.contains(this) && config.isInt(this)) {
      return config.getInt(this, default)
    }

    log.warning("Config: ${sub.currentPath}.$this has no value")
    return default
  }

  companion object {
    val instance: Settings
      get() = IntelliDoors.getInstance().settings

    operator fun get(material: Material): TypeSettings? {
      return instance[material]
    }
  }

  class TypeSettings(
    val type: Type,
    var singleInteract: Boolean,
    var singleInteractReset: Boolean,
    var singleInteractResetTicks: Int,
    var singleRedstone: Boolean,
    var singleRedstoneReset: Boolean,
    var singleRedstoneResetTicks: Int,
    var pairInteract: Boolean,
    var pairInteractSync: Boolean,
    var pairInteractReset: Boolean,
    var pairInteractResetTicks: Int,
    var pairRedstone: Boolean,
    var pairRedstoneSync: Boolean,
    var pairRedstoneReset: Boolean,
    var pairRedstoneResetTicks: Int
  );

  enum class Type {
    DOOR, TRAP, GATE
  }
}
