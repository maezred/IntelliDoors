package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.intData
import net.moltendorf.bukkit.intellidoors.settings.Settings
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
abstract class DoubleDoor private constructor
(val left: SingleDoor, val right: SingleDoor, open: Boolean, settings: Settings) : BaseDoor(settings), Door.Group {
  override val location = left.location.toVector().getMidpoint(right.location.toVector()).toLocation(left.location.world)
  override val type = left.type

  override val facing: BlockFace
    get() = left.facing

  override val powered: Boolean
    get() = left.powered || right.powered

  override var open = open
    set(value) {
      left.open = value
      right.open = value
      field = value
    }

  override fun update(): Boolean {
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
    operator fun invoke(door: SingleDoor, settings: Settings): DoubleDoor? {
      val left: Door
      val right: Door

      if (door.left) {
        left = door
        right = otherDoor(door, door.topBlock.getRelative(door.facing)) ?: return null
      } else {
        right = door
        left = otherDoor(door, door.topBlock.getRelative(door.facing, -1)) ?: return null
      }

      return if (door.type == Material.IRON_DOOR_BLOCK) {
        Iron(left, right, door.open, settings)
      } else {
        Wood(left, right, door.open, settings)
      }
    }

    private fun otherDoor(door: SingleDoor, block: Block): SingleDoor? {
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

  private class Iron : DoubleDoor, Door.Iron {
    constructor(left: SingleDoor, right: SingleDoor, open: Boolean, settings: Settings) : super(left, right, open, settings)
  }

  private class Wood : DoubleDoor, Door.Wood {
    constructor(left: SingleDoor, right: SingleDoor, open: Boolean, settings: Settings) : super(left, right, open, settings)
  }
}
