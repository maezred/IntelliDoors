package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.enabled
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot.HAND

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class Interact() : Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun PlayerInteractEventHandler(event: PlayerInteractEvent) {
    if (!enabled) {
      return
    }

    if (event.action == Action.RIGHT_CLICK_BLOCK && event.hand == HAND) {
      val (door, onDoor) = DoorEvent(event.clickedBlock) ?: return

      if (door.onInteract(onDoor)) {
        event.isCancelled = true
      }
    }
  }
}
