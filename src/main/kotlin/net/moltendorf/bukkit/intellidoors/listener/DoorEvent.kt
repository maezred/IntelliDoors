package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.controller.*
import net.moltendorf.bukkit.intellidoors.settings
import net.moltendorf.bukkit.intellidoors.settings.Variations
import org.bukkit.block.Block

/**
 * Created by moltendorf on 2016-06-03.
 */

data class DoorEvent private constructor(val door: Door, val onDoor: Door = door) {
  companion object {
    operator fun invoke(block: Block): DoorEvent? {
      val variation = settings[block.type] ?: return null

      return when (variation.type) {
        Variations.Type.DOOR -> {
          val singleDoor = SingleDoor(block, variation.single) ?: return null
          DoorEvent(DoubleDoor(singleDoor, variation.pair) ?: singleDoor, singleDoor)
        }

        Variations.Type.TRAP -> {
          val trapDoor = TrapDoor(block, variation.single) ?: return null
          DoorEvent(MultiTrapDoor(trapDoor, variation.pair) ?: trapDoor, trapDoor)
        }

        Variations.Type.GATE -> DoorEvent(FenceGate(block, variation.single) ?: return null)
      }
    }
  }
}
