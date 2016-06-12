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
class FenceGate private constructor(block: Block, settings: Settings) : SimpleDoor(block, settings), Door.Wood {
  override val facing: BlockFace
    get() = FACING[state.intData and 3]

  override fun sound(open: Boolean): Sound {
    return if (open) Sound.BLOCK_FENCE_GATE_OPEN else Sound.BLOCK_FENCE_GATE_CLOSE
  }

  companion object {
    val FACING = listOf(BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST)

    operator fun invoke(block: Block, settings: Settings): FenceGate? {
      return when (block.type) {
        Material.ACACIA_FENCE_GATE,
        Material.BIRCH_FENCE_GATE,
        Material.DARK_OAK_FENCE_GATE,
        Material.FENCE_GATE,
        Material.JUNGLE_FENCE_GATE,
        Material.SPRUCE_FENCE_GATE -> FenceGate(block, settings)
        else -> null
      }
    }
  }
}
