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
    instance = this

    // Construct new settings.
    settings = Settings()

    // Are we enabled?
    enabled = settings.enabled

    // Register listeners.
    val manager = server.pluginManager

    manager.registerEvents(Interact(), this)
    manager.registerEvents(Redstone(), this)
  }

  override fun onDisable() {
    enabled = false
  }

  companion object {
    var enabled = false
      private set

    lateinit var instance: IntelliDoors
      private set
  }
}
