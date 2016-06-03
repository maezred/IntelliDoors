package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.IntelliDoors
import net.moltendorf.bukkit.intellidoors.Settings
import net.moltendorf.bukkit.intellidoors.controller.*
import org.bukkit.block.Block

/**
 * Created by moltendorf on 2016-06-03.
 */

data class DoorEvent private constructor(val door: Door, val onDoor: Door = door) {
  companion object {
    operator fun invoke(block: Block): DoorEvent? {
      val settings = IntelliDoors.instance.settings[block.type] ?: return null

      return when (settings.type) {
        Settings.Type.DOOR -> {
          val singleDoor = SingleDoor(block, settings) ?: return null
          DoorEvent(DoubleDoor(singleDoor, settings) ?: singleDoor, singleDoor)
        }

        Settings.Type.TRAP -> DoorEvent(TrapDoor(block, settings) ?: return null)
        Settings.Type.GATE -> DoorEvent(FenceGate(block, settings) ?: return null)
      }
    }
  }
}
