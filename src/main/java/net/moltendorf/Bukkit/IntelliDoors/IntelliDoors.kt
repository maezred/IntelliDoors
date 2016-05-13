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
  var settings: Settings? = null
    private set

  override fun onEnable() {
    instance = this

    // Construct new settings.
    settings = Settings()

    // Register listeners.
    val manager = server.pluginManager

    manager.registerEvents(Interact(), this)
    manager.registerEvents(Redstone(), this)
  }

  override fun onDisable() {
    instance = null
  }

  companion object {
    // Main instance.
    var instance: IntelliDoors? = null
      private set

    fun DebugByte(bite: Byte) {
      instance?.server?.broadcastMessage(String.format("%4s", Integer.toBinaryString(bite.toInt())).replace(' ', '0'))
    }

    fun DebugString(string: String) {
      instance?.server?.broadcastMessage(string)
    }
  }
}
