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
class SingleDoor private constructor
(val top: Block, bottom: Block, settings: Settings.TypeSettings) : AbstractDoor(bottom, settings) {
  override val location = bottom.location.toVector().getMidpoint(top.location.toVector()).toLocation(bottom.location.world)

  val left: Boolean
    get() = topData and 1 == 0

  val right: Boolean
    get() = topData and 1 == 1

  override val powered: Boolean
    get() = super.powered || (top.isBlockIndirectlyPowered && !top.hasMetadata(UNPOWERED))

  var topData = top.data.toInt()
    private set

  var bottomData: Int
    get() = data
    private set(value) {
      data = value
    }

  override fun clearUnpowered() {
    super.clearUnpowered()

    top.removeMetadata(UNPOWERED, IntelliDoors.instance)
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
    operator fun invoke(block: Block, settings: Settings.TypeSettings): SingleDoor? {
      return when (block.type) {
        Material.IRON_DOOR_BLOCK,
        Material.ACACIA_DOOR,
        Material.BIRCH_DOOR,
        Material.DARK_OAK_DOOR,
        Material.JUNGLE_DOOR,
        Material.SPRUCE_DOOR,
        Material.WOODEN_DOOR -> {
          val data = block.data.toInt()
          val top
            : Block
          val bottom
            : Block

          if (data and 8 == 8) {
            // Top of door.
            top = block
            bottom = block.getRelative(BlockFace.DOWN)
          } else {
            // Bottom of door.
            top = block.getRelative(BlockFace.UP)
            bottom = block
          }

          if (top.type == bottom.type) SingleDoor(top, bottom, settings) else null
        }
        else -> null
      }
    }
  }
}