package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.IntelliDoors
import net.moltendorf.bukkit.intellidoors.Settings
import net.moltendorf.bukkit.intellidoors.controller.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPhysicsEvent

/**
 * Created by moltendorf on 15/05/23.
 */
class Redstone() : Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun blockPhysicsEventHandler(event: BlockPhysicsEvent) {
    if (!IntelliDoors.enabled) {
      return
    }

    val block = event.block
    val settings = IntelliDoors.instance.settings[block.type] ?: return

    var onDoor: Door? = null

    val door = when (settings.type) {
      Settings.Type.DOOR -> {
        val singleDoor = SingleDoor(block, settings) ?: return
        onDoor = singleDoor
        DoubleDoor(singleDoor, settings) ?: singleDoor
      }

      Settings.Type.TRAP -> TrapDoor(block, settings)
      Settings.Type.GATE -> FenceGate(block, settings)
    } ?: return

    if (door.onRedstone(onDoor ?: door)) {
      event.isCancelled = true
    }
  }
}
