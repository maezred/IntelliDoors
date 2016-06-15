package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.settings.Settings
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import java.util.*

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
abstract class MultiTrapDoor private constructor
(val left: List<TrapDoor>, val right: List<TrapDoor>, open: Boolean, settings: Settings) : BaseDoor(settings), Door.Group {
  override val location = left[0].location.toVector().getMidpoint(left[left.size - 1].location.toVector())
    .getMidpoint(right[0].location.toVector().getMidpoint(right[right.size - 1].location.toVector()))
    .toLocation(left[0].location.world)

  override val type = left[0].type

  override val facing: BlockFace
    get() = left[0].facing

  override val powered: Boolean
    get() {
      for (i in 0..left.size - 1) {
        if (left[i].powered || right[i].powered) {
          return true
        }
      }

      return false
    }

  override var open = open
    set(value) {
      for (i in 0..left.size - 1) {
        left[i].open = value
        right[i].open = value
      }

      field = value
    }

  override fun update(): Boolean {
    var updated = false

    for (i in 0..left.size - 1) {
      if (left[i].update()) {
        right[i].update()

        updated = true
      }

      if (right[i].update()) {
        updated = true
      }
    }

    return updated
  }

  companion object {
    operator fun invoke(door: TrapDoor, settings: Settings): MultiTrapDoor? {
      var left: TrapDoor
      var right: TrapDoor

      left = door
      right = TrapDoor(door.block.getRelative(left.facing), door.settings) ?: return null

      val leftFacing = left.facing
      val rightFacing = right.facing

      if (leftFacing !== rotate(rightFacing, 2)) {
        return null
      }

      val leftList = ArrayList<TrapDoor>()
      val rightList = ArrayList<TrapDoor>()
      var direction = rotate(left.facing, 1)

      leftList.add(left)
      rightList.add(right)

      for (i in 1..9) {
        left = TrapDoor(left.block.getRelative(direction), door.settings) ?: break
        right = TrapDoor(right.block.getRelative(direction), door.settings) ?: break

        if (left.facing !== leftFacing || right.facing !== rightFacing) {
          break
        }

        leftList.add(left)
        rightList.add(right)
      }

      left = leftList[0]
      right = rightList[0]

      direction = rotate(left.facing, 3)

      for (i in leftList.size..9) {
        left = TrapDoor(left.block.getRelative(direction), door.settings) ?: break
        right = TrapDoor(right.block.getRelative(direction), door.settings) ?: break

        if (left.facing !== leftFacing || right.facing !== rightFacing) {
          break
        }

        leftList.add(0, left)
        rightList.add(0, right)
      }

      return if (door.type == Material.IRON_TRAPDOOR) {
        Iron(leftList, rightList, door.open, settings)
      } else {
        Wood(leftList, rightList, door.open, settings)
      }
    }
  }

  private class Iron : MultiTrapDoor, Door.Iron {
    constructor(left: List<TrapDoor>, right: List<TrapDoor>, open: Boolean, settings: Settings)
    : super(left, right, open, settings)

    override fun sound(open: Boolean): Sound {
      return if (open) Sound.BLOCK_IRON_TRAPDOOR_OPEN else Sound.BLOCK_IRON_TRAPDOOR_CLOSE
    }
  }

  private class Wood : MultiTrapDoor, Door.Wood {
    constructor(left: List<TrapDoor>, right: List<TrapDoor>, open: Boolean, settings: Settings)
    : super(left, right, open, settings)

    override fun sound(open: Boolean): Sound {
      return if (open) Sound.BLOCK_WOODEN_TRAPDOOR_OPEN else Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE
    }
  }
}
