package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.IntelliDoors
import net.moltendorf.bukkit.intellidoors.Settings
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class DoubleDoor private constructor
(val left: SingleDoor, val right: SingleDoor, open: Boolean, settings: Settings.TypeSettings) : Door(settings) {
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

  override fun onInteract(onDoor: Door): Boolean {
    return if (settings.pairInteract && settings.pairInteractSync) {
      val isOpen = !open

      when (type) {
        Material.IRON_DOOR_BLOCK, Material.IRON_TRAPDOOR -> location.world.playSound(location, sound(isOpen), 1f, 1f)
        else -> onDoor.overrideOpen(isOpen)
      }

      open = isOpen

      if (settings.pairInteractReset) {
        val timer = IntelliDoors.instance.timer

        if (isOpen) {
          timer.shutDoorIn(this, settings.pairInteractResetTicks)
        } else {
          timer.cancel(this)
        }
      }

      false
    } else {
      onDoor.onInteract(onDoor)
    }
  }

  override fun onRedstone(onDoor: Door): Boolean {
    return if (settings.pairRedstone && settings.pairRedstoneSync) {
      val doorPowered = powered

      if (settings.pairRedstoneReset) {
        val timer = IntelliDoors.instance.timer

        if (doorPowered) {
          timer.cancel(this)
        } else {
          timer.shutDoorIn(this, settings.pairRedstoneResetTicks)
          return true
        }
      }

      open = doorPowered
      true
    } else {
      false
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
    operator fun invoke(door: SingleDoor, settings: Settings.TypeSettings): DoubleDoor? {
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
        val otherDoor = SingleDoor(otherBlock, settings) ?: return null

        if (facing == otherDoor.facing && left == otherDoor.right) {
          return if (left) {
            DoubleDoor(door, otherDoor, door.open, settings)
          } else {
            DoubleDoor(otherDoor, door, door.open, settings)
          }
        }
      }

      return null
    }
  }
}
