package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.settings.Settings
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
abstract class SingleDoor private constructor
(val topBlock: Block, bottom: Block, settings: Settings) : SimpleDoor(bottom, settings) {
  val topState = topBlock.state

  override val location = bottom.location.toVector().getMidpoint(topState.location.toVector()).toLocation(bottom.location.world)

  val left: Boolean
    get() = topData and 1 == 0

  val right: Boolean
    get() = topData and 1 == 1

  override val powered: Boolean
    get() = super.powered || topBlock.isBlockIndirectlyPowered

  var topData = topState.rawData.toInt()
    private set

  var bottomData: Int
    get() = data
    private set(value) {
      data = value
    }

  companion object {
    operator fun invoke(block: Block, settings: Settings): SingleDoor? {
      when (block.type) {
        Material.IRON_DOOR_BLOCK,
        Material.ACACIA_DOOR,
        Material.BIRCH_DOOR,
        Material.DARK_OAK_DOOR,
        Material.JUNGLE_DOOR,
        Material.SPRUCE_DOOR,
        Material.WOODEN_DOOR -> {
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

          if (top.type == bottom.type) {
            return if (block.type == Material.IRON_DOOR_BLOCK) {
              Iron(top, bottom, settings)
            } else {
              Wood(top, bottom, settings)
            }
          }
        }
      }

      return null
    }
  }

  private class Iron : SingleDoor, Door.Iron {
    constructor(top: Block, bottom: Block, settings: Settings) : super(top, bottom, settings)
  }

  private class Wood : SingleDoor, Door.Wood {
    constructor(top: Block, bottom: Block, settings: Settings) : super(top, bottom, settings)
  }
}
