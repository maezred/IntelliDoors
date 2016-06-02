package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.IntelliDoors
import net.moltendorf.bukkit.intellidoors.Settings
import net.moltendorf.bukkit.intellidoors.controller.Door
import net.moltendorf.bukkit.intellidoors.controller.DoubleDoor
import net.moltendorf.bukkit.intellidoors.controller.SingleDoor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockRedstoneEvent
import org.bukkit.metadata.FixedMetadataValue

/**
 * Created by moltendorf on 15/05/23.
 */
class Redstone() : Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun blockRedstoneEventHandler(event: BlockRedstoneEvent) {
    if (!IntelliDoors.enabled) {
      return
    }

    val block = event.block
    val settings = IntelliDoors.instance.settings[block.type] ?: return

    if (settings.type == Settings.Type.DOOR && settings.pairRedstone && settings.pairRedstoneSync) {
      val powered = event.newCurrent > 0

      if (powered == (event.oldCurrent > 0)) {
        if (powered) {
          block.removeMetadata(Door.UNPOWERED, IntelliDoors.instance)
        }

        return
      }

      val single = SingleDoor[block] ?: return
      val double = DoubleDoor[single] ?: return

      double.onRedstone(single, powered)

      if (!powered && double.open) {
        event.newCurrent = 1
        block.setMetadata(Door.UNPOWERED, FixedMetadataValue(IntelliDoors.instance, true))
      }
    }
  }
}
