package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.IntelliDoors
import net.moltendorf.bukkit.intellidoors.Settings
import net.moltendorf.bukkit.intellidoors.controller.DoubleDoor
import net.moltendorf.bukkit.intellidoors.controller.FenceGate
import net.moltendorf.bukkit.intellidoors.controller.SingleDoor
import net.moltendorf.bukkit.intellidoors.controller.TrapDoor
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

    val instance = IntelliDoors.instance
    val block = event.block
    val settings = instance.settings[block.type] ?: return
    val type = settings.type
    val timer = instance.timer

    val door = when (type) {
      Settings.Type.DOOR -> {
        val singleDoor = SingleDoor(block, settings) ?: return

        if (settings.pairRedstone && settings.pairRedstoneSync) {
          val doubleDoor = DoubleDoor(singleDoor, settings)

          if (doubleDoor != null) {
            event.isCancelled = true

            val doorPowered = doubleDoor.powered

            if (settings.pairRedstoneReset) {
              if (doorPowered) {
                timer.cancel(doubleDoor)
              } else {
                timer.shutDoorIn(doubleDoor, settings.pairRedstoneResetTicks)
                return
              }
            }

            doubleDoor.open = doorPowered

            return
          }
        }

        singleDoor
      }
      Settings.Type.TRAP -> TrapDoor(block, settings)
      Settings.Type.GATE -> FenceGate(block, settings)
    } ?: return

    if (settings.singleRedstone) {
      event.isCancelled = true

      val doorPowered = door.powered

      if (settings.singleRedstoneReset) {
        if (doorPowered) {
          timer.cancel(door)
        } else {
          timer.shutDoorIn(door, settings.singleRedstoneResetTicks)
          return
        }
      }

      door.open = doorPowered
    }
  }
}
