package net.moltendorf.Bukkit.IntelliDoors

import net.moltendorf.Bukkit.IntelliDoors.listener.Interact
import net.moltendorf.Bukkit.IntelliDoors.listener.Redstone
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class IntelliDoors : JavaPlugin() {
  // Variable data.
  lateinit var settings: Settings
    private set

  override fun onEnable() {
    // Construct new settings.
    settings = Settings(this)

    // Register listeners.
    val manager = server.pluginManager

    manager.registerEvents(Interact(this), this)
    manager.registerEvents(Redstone(this), this)
  }
}
