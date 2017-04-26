package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import net.moltendorf.bukkit.intellidoors.settings.*
import org.bukkit.Material.*
import org.bukkit.block.*
import org.bukkit.block.BlockFace.*

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
abstract class SingleDoor private constructor(val topBlock : Block, bottom : Block, settings : Settings) : SimpleDoor(bottom, settings) {
  val topState = topBlock.state!!

  override val location = bottom.location.toVector().getMidpoint(topState.location.toVector()).toLocation(bottom.location.world)!!

  val left get() = topState.intData and 1 == 0
  val right get() = topState.intData and 1 == 1

  override val facing get() = FACING[state.intData and 3]
  override val powered get() = super.powered || topBlock.isBlockIndirectlyPowered

  companion object {
    val FACING = listOf(EAST, SOUTH, WEST, NORTH)

    operator fun invoke(block : Block, settings : Settings) : SingleDoor? {
      return when (block.type) {
        IRON_DOOR_BLOCK, ACACIA_DOOR, BIRCH_DOOR, DARK_OAK_DOOR, JUNGLE_DOOR, SPRUCE_DOOR, WOODEN_DOOR -> {
          val top : Block
          val bottom : Block

          if (block.intData and 8 == 8) {
            // Top of door.
            top = block
            bottom = block.getRelative(DOWN)
          } else {
            // Bottom of door.
            top = block.getRelative(UP)
            bottom = block
          }

          if (top.type == bottom.type) {
            when (block.type) {
              IRON_DOOR_BLOCK -> Iron(top, bottom, settings)
              else -> Wood(top, bottom, settings)
            }
          } else {
            null
          }
        }
        else -> null
      }
    }
  }

  private class Iron(top : Block, bottom : Block, settings : Settings) : SingleDoor(top, bottom, settings), IronType
  private class Wood(top : Block, bottom : Block, settings : Settings) : SingleDoor(top, bottom, settings), WoodType
}
