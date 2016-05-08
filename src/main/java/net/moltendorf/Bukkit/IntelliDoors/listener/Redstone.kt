package net.moltendorf.Bukkit.IntelliDoors.listener

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
class Redstone : Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  fun redstoneChangeEventHandler(event: BlockRedstoneEvent) {
    val block = event.block
    val material = block.type
    val settings = Settings[material]

    if (settings != null) {
      val type = settings.type

      if (type == Settings.Type.DOOR && settings.pairRedstone && settings.pairRedstoneSync) {
        val single = SingleDoor.getDoor(block)

        DoubleDoor.getDoor(single)?.wasToggled(single)
      }
    }
  }
}
