package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.IntelliDoors
import net.moltendorf.bukkit.intellidoors.Settings
import net.moltendorf.bukkit.intellidoors.controller.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class Interact() : Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun PlayerInteractEventHandler(event: PlayerInteractEvent) {
    if (!IntelliDoors.enabled) {
      return
    }

    if (event.action == Action.RIGHT_CLICK_BLOCK) {
      val block = event.clickedBlock
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

      if (door.onInteract(onDoor ?: door)) {
        event.isCancelled = true
      }
    }
  }
}
