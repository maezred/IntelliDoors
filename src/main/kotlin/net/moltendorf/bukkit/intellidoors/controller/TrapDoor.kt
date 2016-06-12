package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.intData
import net.moltendorf.bukkit.intellidoors.settings.Settings
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 15/05/23.
 */
abstract class TrapDoor private constructor(block: Block, settings: Settings) : SimpleDoor(block, settings) {
  override val facing: BlockFace
    get() = FACING[state.intData and 3]

  companion object {
    val FACING = listOf(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST)

    operator fun invoke(block: Block, settings: Settings): TrapDoor? {
      return when (block.type) {
        Material.IRON_TRAPDOOR -> Iron(block, settings)
        Material.TRAP_DOOR -> Wood(block, settings)
        else -> null
      }
    }
  }

  private class Iron(block: Block, settings: Settings) : TrapDoor(block, settings), Door.Iron {
    override fun sound(open: Boolean): Sound {
      return if (open) Sound.BLOCK_IRON_TRAPDOOR_OPEN else Sound.BLOCK_IRON_TRAPDOOR_CLOSE
    }
  }

  private class Wood(block: Block, settings: Settings) : TrapDoor(block, settings), Door.Wood {
    override fun sound(open: Boolean): Sound {
      return if (open) Sound.BLOCK_WOODEN_TRAPDOOR_OPEN else Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE
    }
  }
}
