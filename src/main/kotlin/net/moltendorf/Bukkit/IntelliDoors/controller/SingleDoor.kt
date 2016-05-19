package net.moltendorf.Bukkit.IntelliDoors.controller

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class SingleDoor(val top: Block, bottom: Block) : AbstractDoor(bottom) {
  val left: Boolean
    get() = topData and 1 == 0

  val right: Boolean
    get() = topData and 1 == 1

  override val location = top.location.subtract(bottom.location).add(top.location)

  var topData = top.data.toInt()
    private set

  var bottomData: Int
    get() = data
    private set(value) {
      data = value
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
    operator fun get(block: Block): SingleDoor? {
      val data = block.data.toInt()
      val top: Block
      val bottom: Block

      if (data and 8 == 8) {
        // Top of door.
        top = block
        bottom = block.getRelative(BlockFace.DOWN)
      } else {
        // Bottom of door.
        top = block.getRelative(BlockFace.UP)
        bottom = block
      }

      return if (top.type == bottom.type) SingleDoor(top, bottom) else null
    }
  }
}
