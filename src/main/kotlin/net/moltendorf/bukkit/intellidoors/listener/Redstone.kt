package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.IntelliDoors
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

    val (door, onDoor) = DoorEvent(event.block) ?: return

    if (door.onRedstone(onDoor)) {
      event.isCancelled = true
    }
  }
}
