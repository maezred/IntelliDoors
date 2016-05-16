package net.moltendorf.Bukkit.IntelliDoors.listener

import net.moltendorf.Bukkit.IntelliDoors.IntelliDoors
import net.moltendorf.Bukkit.IntelliDoors.Settings
import net.moltendorf.Bukkit.IntelliDoors.controller.DoubleDoor
import net.moltendorf.Bukkit.IntelliDoors.controller.SingleDoor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockRedstoneEvent

/**
 * Created by moltendorf on 15/05/23.
 */
class Redstone(val instance: IntelliDoors) : Listener {
  val settings = instance.settings

  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun blockRedstoneEventHandler(event: BlockRedstoneEvent) {
    val block = event.block
    val settings = settings[block.type] ?: return

    if (settings.type == Settings.Type.DOOR && settings.pairRedstone && settings.pairRedstoneSync) {
      val single = SingleDoor[block] ?: return

      DoubleDoor[single]?.onInteract(single)
    }
  }
}
