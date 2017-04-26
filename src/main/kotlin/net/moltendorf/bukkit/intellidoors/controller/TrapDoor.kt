package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import net.moltendorf.bukkit.intellidoors.settings.*
import org.bukkit.Material.*
import org.bukkit.Sound.*
import org.bukkit.block.*
import org.bukkit.block.BlockFace.*

/**
 * Created by moltendorf on 15/05/23.
 */
abstract class TrapDoor private constructor(block : Block, settings : Settings) : SimpleDoor(block, settings) {
  override val facing get() = FACING[state.intData and 3]

  companion object {
    val FACING = listOf(NORTH, SOUTH, WEST, EAST)

    operator fun invoke(block : Block, settings : Settings) : TrapDoor? {
      return when (block.type) {
        IRON_TRAPDOOR -> Iron(block, settings)
        TRAP_DOOR -> Wood(block, settings)
        else -> null
      }
    }
  }

  private class Iron(block : Block, settings : Settings) : TrapDoor(block, settings), IronType {
    override fun sound(open : Bool) = if (open) BLOCK_IRON_TRAPDOOR_OPEN else BLOCK_IRON_TRAPDOOR_CLOSE
  }

  private class Wood(block : Block, settings : Settings) : TrapDoor(block, settings), WoodType {
    override fun sound(open : Bool) = if (open) BLOCK_WOODEN_TRAPDOOR_OPEN else BLOCK_WOODEN_TRAPDOOR_CLOSE
  }
}
