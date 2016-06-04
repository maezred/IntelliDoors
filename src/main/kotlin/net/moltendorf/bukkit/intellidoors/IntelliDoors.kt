package net.moltendorf.bukkit.intellidoors

import net.moltendorf.bukkit.intellidoors.listener.Interact
import net.moltendorf.bukkit.intellidoors.listener.Redstone
import net.moltendorf.bukkit.intellidoors.settings.GlobalSettings
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class IntelliDoors : JavaPlugin() {
  // Variable data.
  lateinit var settings: GlobalSettings
    private set
  lateinit var timer: Timer
    private set

  override fun onEnable() {
    instance = this

    // Construct new settings.
    settings = GlobalSettings()

    // Construct new timer.
    timer = Timer()

    // Are we enabled?
    enabled = settings.enabled

    if (enabled) {
      // Register listeners.
      val manager = server.pluginManager

      if (settings.interact) {
        manager.registerEvents(Interact(), this)
        logger.info("Enabled interact listeners.")
      }

      if (settings.redstone) {
        manager.registerEvents(Redstone(), this)
        logger.info("Enabled redstone listeners.")
      }
    }
  }

  override fun onDisable() {
    timer.shutAllDoors()

    enabled = false
  }

  companion object {
    var enabled = false
      private set

    lateinit var instance: IntelliDoors
      private set
  }
}
