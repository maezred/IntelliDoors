package net.moltendorf.Bukkit.IntelliDoors;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class Settings {
  public static Settings getInstance() {
    return IntelliDoors.getInstance().settings;
  }

  private boolean enabled = true; // Whether or not the plugin is enabled at all; interface mode.

  private HashMap<Material, TypeSettings> doors = new HashMap<>();

  Settings() {
    IntelliDoors instance = IntelliDoors.getInstance();
    Logger log = instance.getLogger();

    // Make sure the default configuration is saved.
    instance.saveDefaultConfig();

    FileConfiguration config = instance.getConfig();

    enabled = config.getBoolean("enabled", enabled);

    ConfigurationSection doorSection = config.getConfigurationSection("doors");

    if (doorSection != null) {
      for (String key : doorSection.getKeys(false)) {
        ConfigurationSection typeSection = doorSection.getConfigurationSection(key);

        if (typeSection == null) {
          log.warning("Config: door." + key + " is not specified.");

          continue;
        }

        String typeString = typeSection.getString("type");

        if (typeString == null) {
          log.warning("Config: door." + key + ".type has no value.");

          continue;
        }

        TypeSettings.Type type;

        switch (typeString) {
          case "door":
            type = TypeSettings.Type.DOOR;
            break;

          case "trap":
            type = TypeSettings.Type.TRAP;
            break;

          case "gate":
            type = TypeSettings.Type.GATE;
            break;

          default:
            log.warning("Config: door." + key + ".type has invalid value: \"" + typeString + "\".");

            continue;
        }

        Collection<Material> materials = new LinkedList<>();

        for (String materialString : typeSection.getStringList("material")) {
          Material material = Material.getMaterial(materialString);

          if (material == null) {
            log.warning("Config: door." + key + ".material has invalid value: \"" + materialString + "\".");

            continue;
          }

          materials.add(material);
        }

        if (materials.size() == 0) {
          log.warning("Config: door." + key + ".material has no values.");

          continue;
        }

        TypeSettings settings;

				/*
         * Copy pasta. Psh, nobody needed readable code anyway.
				 */

				/*
         * Individuals.
				 */

        boolean iie;

        String sub = "individual.interact.enabled";
        String check = typeSection.getString(sub);

        if (check == null) {
          log.warning("Config: door." + key + "." + sub + " has no value.");

          continue;
        }

        if (check.matches("^(?i:true|false)$")) {
          iie = typeSection.getBoolean(sub);
        } else if (config.contains(check)) {
          iie = config.getBoolean(check, true);
        } else {
          log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

          continue;
        }

        boolean iire;

        sub = "individual.interact.reset.enabled";
        check = typeSection.getString(sub);

        if (check == null) {
          log.warning("Config: door." + key + "." + sub + " has no value.");

          continue;
        }

        if (check.matches("^(?i:true|false)$")) {
          iire = typeSection.getBoolean(sub);
        } else if (config.contains(check)) {
          iire = config.getBoolean(check, true);
        } else {
          log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

          continue;
        }

        int iirt;

        sub = "individual.interact.reset.ticks";
        check = typeSection.getString(sub);

        if (check == null) {
          log.warning("Config: door." + key + "." + sub + " has no value.");

          continue;
        }

        if (check.matches("^[0-9]+$")) {
          iirt = typeSection.getInt(sub);
        } else if (config.contains(check)) {
          iirt = config.getInt(check, 100);
        } else {
          log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

          continue;
        }

        boolean ire;

        sub = "individual.redstone.enabled";
        check = typeSection.getString(sub);

        if (check == null) {
          log.warning("Config: door." + key + "." + sub + " has no value.");

          continue;
        }

        if (check.matches("^(?i:true|false)$")) {
          ire = typeSection.getBoolean(sub);
        } else if (config.contains(check)) {
          ire = config.getBoolean(check, true);
        } else {
          log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

          continue;
        }

        boolean irre;

        sub = "individual.redstone.reset.enabled";
        check = typeSection.getString(sub);

        if (check == null) {
          log.warning("Config: door." + key + "." + sub + " has no value.");

          continue;
        }

        if (check.matches("^(?i:true|false)$")) {
          irre = typeSection.getBoolean(sub);
        } else if (config.contains(check)) {
          irre = config.getBoolean(check, true);
        } else {
          log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

          continue;
        }

        int irrt;

        sub = "individual.redstone.reset.ticks";
        check = typeSection.getString(sub);

        if (check == null) {
          log.warning("Config: door." + key + "." + sub + " has no value.");

          continue;
        }

        if (check.matches("^(?i:true|false)$")) {
          irrt = typeSection.getInt(sub);
        } else if (config.contains(check)) {
          irrt = config.getInt(check, 20);
        } else {
          log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

          continue;
        }

        if (type == TypeSettings.Type.DOOR) {
          /*
           * Pairs.
           */

          boolean pie;

          sub = "pair.interact.enabled";
          check = typeSection.getString(sub);

          if (check == null) {
            log.warning("Config: door." + key + "." + sub + " has no value.");

            continue;
          }

          if (check.matches("^(?i:true|false)$")) {
            pie = typeSection.getBoolean(sub);
          } else if (config.contains(check)) {
            pie = config.getBoolean(check, true);
          } else {
            log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

            continue;
          }

          boolean pis;

          sub = "pair.interact.sync";
          check = typeSection.getString(sub);

          if (check == null) {
            log.warning("Config: door." + key + "." + sub + " has no value.");

            continue;
          }

          if (check.matches("^(?i:true|false)$")) {
            pis = typeSection.getBoolean(sub);
          } else if (config.contains(check)) {
            pis = config.getBoolean(check, true);
          } else {
            log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

            continue;
          }

          boolean pire;

          sub = "pair.interact.reset.enabled";
          check = typeSection.getString(sub);

          if (check == null) {
            log.warning("Config: door." + key + "." + sub + " has no value.");

            continue;
          }

          if (check.matches("^(?i:true|false)$")) {
            pire = typeSection.getBoolean(sub);
          } else if (config.contains(check)) {
            pire = config.getBoolean(check, true);
          } else {
            log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

            continue;
          }

          int pirt;

          sub = "pair.interact.reset.ticks";
          check = typeSection.getString(sub);

          if (check == null) {
            log.warning("Config: door." + key + "." + sub + " has no value.");

            continue;
          }

          if (check.matches("^[0-9]+$")) {
            pirt = typeSection.getInt(sub);
          } else if (config.contains(check)) {
            pirt = config.getInt(check, 100);
          } else {
            log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

            continue;
          }

          boolean pre;

          sub = "pair.redstone.enabled";
          check = typeSection.getString(sub);

          if (check == null) {
            log.warning("Config: door." + key + "." + sub + " has no value.");

            continue;
          }

          if (check.matches("^(?i:true|false)$")) {
            pre = typeSection.getBoolean(sub);
          } else if (config.contains(check)) {
            pre = config.getBoolean(check, true);
          } else {
            log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

            continue;
          }

          boolean prs;

          sub = "pair.redstone.sync";
          check = typeSection.getString(sub);

          if (check == null) {
            log.warning("Config: door." + key + "." + sub + " has no value.");

            continue;
          }

          if (check.matches("^(?i:true|false)$")) {
            prs = typeSection.getBoolean(sub);
          } else if (config.contains(check)) {
            prs = config.getBoolean(check, true);
          } else {
            log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

            continue;
          }

          boolean prre;

          sub = "pair.redstone.reset.enabled";
          check = typeSection.getString(sub);

          if (check == null) {
            log.warning("Config: door." + key + "." + sub + " has no value.");

            continue;
          }

          if (check.matches("^(?i:true|false)$")) {
            prre = typeSection.getBoolean(sub);
          } else if (config.contains(check)) {
            prre = config.getBoolean(check, true);
          } else {
            log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

            continue;
          }

          int prrt;

          sub = "pair.redstone.reset.ticks";
          check = typeSection.getString(sub);

          if (check == null) {
            log.warning("Config: door." + key + "." + sub + " has no value.");

            continue;
          }

          if (check.matches("^(?i:true|false)$")) {
            prrt = typeSection.getInt(sub);
          } else if (config.contains(check)) {
            prrt = config.getInt(check, 20);
          } else {
            log.warning("Config: door." + key + "." + sub + " has invalid value: \"" + check + "\".");

            continue;
          }

          settings = new TypeSettings(type, iie, iire, iirt, ire, irre, irrt, pie, pis, pire, pirt, pre, prs, prre, prrt);
        } else {
          settings = new TypeSettings(type, iie, iire, iirt, ire, irre, irrt, false, false, false, 0, false, false, false, 0);
        }

        log.info("Configuring " + key + " type.");

        for (Material material : materials) {
          if (doors.containsKey(material)) {
            log.warning("Config: door." + key + ".material already has value: \"" + material.toString() + "\".");

            continue;
          }

          doors.put(material, settings);
        }
      }
    }
  }

  public boolean getEnabled() {
    return enabled;
  }

  public TypeSettings getSettings(Material material) {
    return doors.get(material);
  }

  public static class TypeSettings {
    private Type type;

    private boolean individualInteractEnabled;
    private boolean individualInteractResetEnabled;
    private int individualInteractResetTicks;

    private boolean individualRedstoneEnabled;
    private boolean individualRedstoneResetEnabled;
    private int individualRedstoneResetTicks;

    private boolean pairInteractEnabled;
    private boolean pairInteractSync;
    private boolean pairInteractResetEnabled;
    private int pairInteractResetTicks;

    private boolean pairRedstoneEnabled;
    private boolean pairRedstoneSync;
    private boolean pairRedstoneResetEnabled;
    private int pairRedstoneResetTicks;

    TypeSettings(Type t, boolean iie, boolean iire, int iirt, boolean ire, boolean irre, int irrt, boolean pie, boolean pis,
                 boolean pire, int pirt, boolean pre, boolean prs, boolean prre, int prrt) {
      type = t;

      individualInteractEnabled = iie;
      individualInteractResetEnabled = iire;
      individualInteractResetTicks = iirt;

      individualRedstoneEnabled = ire;
      individualRedstoneResetEnabled = irre;
      individualRedstoneResetTicks = irrt;

      pairInteractEnabled = pie;
      pairInteractSync = pis;
      pairInteractResetEnabled = pire;
      pairInteractResetTicks = pirt;

      pairRedstoneEnabled = pre;
      pairRedstoneSync = prs;
      pairRedstoneResetEnabled = prre;
      pairRedstoneResetTicks = prrt;
    }

    public Type getType() {
      return type;
    }

    public boolean isIndividualInteractEnabled() {
      return individualInteractEnabled;
    }

    public boolean isIndividualInteractResetEnabled() {
      return individualInteractResetEnabled;
    }

    public int getIndividualInteractResetTicks() {
      return individualInteractResetTicks;
    }

    public boolean isIndividualRedstoneEnabled() {
      return individualRedstoneEnabled;
    }

    public boolean isIndividualRedstoneResetEnabled() {
      return individualRedstoneResetEnabled;
    }

    public int getIndividualRedstoneResetTicks() {
      return individualRedstoneResetTicks;
    }

    public boolean isPairInteractEnabled() {
      return pairInteractEnabled;
    }

    public boolean isPairInteractSync() {
      return pairInteractSync;
    }

    public boolean isPairInteractResetEnabled() {
      return pairInteractResetEnabled;
    }

    public int getPairInteractResetTicks() {
      return pairInteractResetTicks;
    }

    public boolean isPairRedstoneEnabled() {
      return pairRedstoneEnabled;
    }

    public boolean isPairRedstoneSync() {
      return pairRedstoneSync;
    }

    public boolean isPairRedstoneResetEnabled() {
      return pairRedstoneResetEnabled;
    }

    public int getPairRedstoneResetTicks() {
      return pairRedstoneResetTicks;
    }

    public enum Type {
      DOOR, TRAP, GATE
    }
  }
}
