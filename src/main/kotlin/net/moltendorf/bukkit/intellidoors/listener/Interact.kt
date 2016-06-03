package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.IntelliDoors
import net.moltendorf.bukkit.intellidoors.Settings
import net.moltendorf.bukkit.intellidoors.controller.DoubleDoor
import net.moltendorf.bukkit.intellidoors.controller.FenceGate
import net.moltendorf.bukkit.intellidoors.controller.SingleDoor
import net.moltendorf.bukkit.intellidoors.controller.TrapDoor
import org.bukkit.Material
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
      val instance = IntelliDoors.instance
      val block = event.clickedBlock
      val material = block.type
      val settings = instance.settings[material] ?: return
      val type = settings.type
      val timer = instance.timer

      val door = when (type) {
        Settings.Type.DOOR -> {
          val singleDoor = SingleDoor(block, settings) ?: return

          if (settings.pairInteract && settings.pairInteractSync) {
            val doubleDoor = DoubleDoor(singleDoor, settings)

            if (doubleDoor != null) {
              doubleDoor.onInteract(singleDoor)

              if (settings.pairInteractReset) {
                if (doubleDoor.open) {
                  timer.shutDoorIn(doubleDoor, settings.pairInteractResetTicks)
                } else {
                  timer.cancel(doubleDoor)
                }
              }

              return
            }
          }

          singleDoor
        }

        Settings.Type.TRAP -> TrapDoor(block, settings)
        Settings.Type.GATE -> FenceGate(block, settings)
      } ?: return

      if (settings.singleInteract) {
        door.onInteract(door)

        if (settings.singleInteractReset) {
          if (door.open) {
            timer.shutDoorIn(door, settings.singleInteractResetTicks)
          } else {
            timer.cancel(door);
          }
        }
      } else {
        when (material) {
          Material.IRON_DOOR_BLOCK, Material.IRON_TRAPDOOR -> {
            // Do nothing as it won't open anyway.
          }
          else -> event.isCancelled = true // Prevent door from opening.
        }
      }
    }
  }
}
