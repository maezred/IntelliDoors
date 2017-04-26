package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import net.moltendorf.bukkit.intellidoors.settings.*
import org.bukkit.Material.*
import org.bukkit.block.*

/**
 * Created by moltendorf on 15/05/23.

 * DoubleDoor controller class.
 */
abstract class DoubleDoor private constructor(val left : SingleDoor, val right : SingleDoor, open : Bool, settings : Settings) :
  Door(settings), Sync {
  override val location = left.location.toVector().getMidpoint(right.location.toVector()).toLocation(left.location.world)!!
  override val type = left.type

  override val facing get() = left.facing
  override val powered get() = left.powered || right.powered

  override var open = open
    set(value) {
      left.open = value
      right.open = value
      field = value
    }

  override fun update() : Bool {
    if (left.update()) {
      right.update()

      return true
    }

    if (right.update()) {
      return true
    }

    return false
  }

  companion object {
    operator fun invoke(door : SingleDoor, settings : Settings) : DoubleDoor? {
      val left : DoorInterface
      val right : DoorInterface

      if (door.left) {
        left = door
        right = otherDoor(door, door.topBlock.getRelative(rotate(door.facing, 1))) ?: return null
      } else {
        right = door
        left = otherDoor(door, door.topBlock.getRelative(rotate(door.facing, 3))) ?: return null
      }

      return if (door.type == IRON_DOOR_BLOCK) {
        Iron(left, right, door.open, settings)
      } else {
        Wood(left, right, door.open, settings)
      }
    }

    private fun otherDoor(door : SingleDoor, block : Block) : SingleDoor? {
      // Check if it's the same type and also the top of the door.
      if (block.type == door.topState.type && block.intData and 8 == 8) {
        val otherDoor = SingleDoor(block, door.settings) ?: return null

        if (door.facing == otherDoor.facing && door.left == otherDoor.right) {
          return otherDoor
        }
      }

      return null
    }
  }

  private class Iron(left : SingleDoor, right : SingleDoor, open : Bool, settings : Settings) :
    DoubleDoor(left, right, open, settings), IronType

  private class Wood(left : SingleDoor, right : SingleDoor, open : Bool, settings : Settings) :
    DoubleDoor(left, right, open, settings), WoodType
}
