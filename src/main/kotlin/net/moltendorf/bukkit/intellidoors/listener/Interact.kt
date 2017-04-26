package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.*
import org.bukkit.event.*
import org.bukkit.event.block.*
import org.bukkit.event.player.*
import org.bukkit.inventory.EquipmentSlot.*

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class Interact : Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun playerInteractEventHandler(event : PlayerInteractEvent) {
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
