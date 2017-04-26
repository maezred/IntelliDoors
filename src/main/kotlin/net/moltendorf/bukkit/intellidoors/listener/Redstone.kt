package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.*
import org.bukkit.event.*
import org.bukkit.event.block.*

/**
 * Created by moltendorf on 15/05/23.
 */
class Redstone : Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun blockPhysicsEventHandler(event : BlockPhysicsEvent) {
    if (!enabled) {
      return
    }

    val (door, onDoor) = DoorEvent(event.block) ?: return

    if (door.onRedstone(onDoor)) {
      event.isCancelled = true
    }
  }
}
