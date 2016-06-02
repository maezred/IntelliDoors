package net.moltendorf.bukkit.intellidoors.controller

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class DoubleDoor(val left: SingleDoor, val right: SingleDoor, open: Boolean) : Door() {
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

      if (!value) {
        clearUnpowered()
      }
    }

  override fun clearUnpowered() {
    left.clearUnpowered()
    right.clearUnpowered()
  }

  override fun onInteract(onDoor: Door) {
    val isOpen = !open

    when (type) {
      Material.IRON_DOOR_BLOCK, Material.IRON_TRAPDOOR -> location.world.playSound(location, sound(isOpen), 1f, 1f)
      else -> onDoor.overrideOpen(isOpen)
    }

    open = isOpen
  }

  override fun onRedstone(onDoor: Door, value: Boolean) {
    if (value) {
      if (!open) {
        open = true
      }
    } else if (open && !powered) {
      open = false
    }
  }

  override fun overrideOpen(value: Boolean) {
    left.overrideOpen(value)
    right.overrideOpen(value)
    open = value
  }

  override fun sound(open: Boolean): Sound {
    return when (type) {
      Material.IRON_DOOR_BLOCK -> {
        if (open) Sound.BLOCK_IRON_DOOR_OPEN else Sound.BLOCK_IRON_DOOR_CLOSE
      }
      Material.ACACIA_DOOR,
      Material.BIRCH_DOOR,
      Material.DARK_OAK_DOOR,
      Material.JUNGLE_DOOR,
      Material.SPRUCE_DOOR,
      Material.WOODEN_DOOR -> {
        if (open) Sound.BLOCK_WOODEN_DOOR_OPEN else Sound.BLOCK_WOODEN_DOOR_CLOSE
      }
      else -> Sound.BLOCK_ANVIL_LAND
    }
  }

  companion object {
    operator fun get(door: SingleDoor): DoubleDoor? {
      val left = door.left
      val facing = door.facing
      val otherBlock: Block

      if (left) {
        otherBlock = door.top.getRelative(facing)
      } else {
        otherBlock = door.top.getRelative(facing, -1)
      }

      // Check if it's the same type and also the top of the door.
      if (otherBlock.type == door.top.type && otherBlock.data.toInt() and 8 == 8) {
        val otherDoor = SingleDoor[otherBlock] ?: return null

        if (facing == otherDoor.facing && left == otherDoor.right) {
          return if (left) DoubleDoor(door, otherDoor, door.open) else DoubleDoor(otherDoor, door, door.open)
        }
      }

      return null
    }
  }
}
