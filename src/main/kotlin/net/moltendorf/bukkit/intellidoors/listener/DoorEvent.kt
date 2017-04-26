package net.moltendorf.bukkit.intellidoors.listener

import net.moltendorf.bukkit.intellidoors.*
import net.moltendorf.bukkit.intellidoors.controller.*
import net.moltendorf.bukkit.intellidoors.settings.Variations.Type.*
import org.bukkit.block.*

/**
 * Created by moltendorf on 2016-06-03.
 */

data class DoorEvent constructor(val door : DoorInterface, val onDoor : DoorInterface = door) {
  companion object {
    operator fun invoke(block : Block) : DoorEvent? {
      val variation = settings[block.type] ?: return null

      return when (variation.type) {
        DOOR -> {
          val singleDoor = SingleDoor(block, variation.single) ?: return null
          DoorEvent(DoubleDoor(singleDoor, variation.pair) ?: singleDoor, singleDoor)
        }

        TRAP -> {
          val trapDoor = TrapDoor(block, variation.single) ?: return null
          DoorEvent(SyncTrapDoor(trapDoor, variation.pair) ?: trapDoor, trapDoor)
        }

        GATE -> DoorEvent(FenceGate(block, variation.single) ?: return null)
      }
    }
  }
}
