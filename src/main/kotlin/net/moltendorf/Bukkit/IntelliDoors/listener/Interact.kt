package net.moltendorf.Bukkit.IntelliDoors.listener

import net.moltendorf.Bukkit.IntelliDoors.Settings
import net.moltendorf.Bukkit.IntelliDoors.controller.DoubleDoor
import net.moltendorf.Bukkit.IntelliDoors.controller.Gate
import net.moltendorf.Bukkit.IntelliDoors.controller.SingleDoor
import net.moltendorf.Bukkit.IntelliDoors.controller.TrapDoor
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
class Interact : Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun PlayerInteractEventHandler(event: PlayerInteractEvent) {
    if (event.action == Action.RIGHT_CLICK_BLOCK) {
      val block = event.clickedBlock
      val material = block.type
      val settings = Settings.instance[material] ?: return
      val type = settings.type

      val door = when (type) {
        Settings.Type.DOOR -> {
          val singleDoor = SingleDoor[block] ?: return

          if (settings.pairInteract && settings.pairInteractSync) {
            val doubleDoor = DoubleDoor[singleDoor]

            if (doubleDoor != null) {
              doubleDoor.onInteract(singleDoor)

              return
            }
          }

          singleDoor
        }

        Settings.Type.TRAP -> TrapDoor.getDoor(block)
        Settings.Type.GATE -> Gate.getDoor(block)
      } ?: return

      if (settings.singleInteract) {
        door.onInteract(door)
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
