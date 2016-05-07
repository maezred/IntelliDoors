package net.moltendorf.Bukkit.IntelliDoors;

import net.moltendorf.Bukkit.IntelliDoors.listener.Interact;
import net.moltendorf.Bukkit.IntelliDoors.listener.Redstone;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class IntelliDoors extends JavaPlugin {
  // Main instance.
  private static IntelliDoors instance = null;

  protected static IntelliDoors getInstance() {
    return instance;
  }

  // Variable data.
  protected Settings settings = null;

  @Override
  public void onEnable() {
    instance = this;

    // Construct new settings.
    settings = new Settings();

    // Register listeners.
    PluginManager manager = getServer().getPluginManager();

    manager.registerEvents(new Interact(), this);
    manager.registerEvents(new Redstone(), this);
  }

  @Override
  public void onDisable() {
    instance = null;
  }

  public static void DebugByte(byte bite) {
    getInstance().getServer().broadcastMessage(String.format("%4s", Integer.toBinaryString(bite)).replace(' ', '0'));
  }

  public static void DebugString(String string) {
    getInstance().getServer().broadcastMessage(string);
  }
}
