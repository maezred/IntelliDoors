package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import net.moltendorf.bukkit.intellidoors.settings.*
import org.bukkit.Material.*
import org.bukkit.Sound.*
import org.bukkit.block.*
import org.bukkit.block.BlockFace.*

/**
 * Created by moltendorf on 15/05/23.
 *
 * FenceGate controller class.
 */
class FenceGate private constructor(block : Block, settings : Settings) : SimpleDoor(block, settings), WoodType {
  override val facing get() = FACING[state.intData and 3]

  override fun sound(open : Bool) = if (open) BLOCK_FENCE_GATE_OPEN else BLOCK_FENCE_GATE_CLOSE

  companion object {
    val FACING = listOf(SOUTH, WEST, NORTH, EAST)

    operator fun invoke(block : Block, settings : Settings) = when (block.type) {
      ACACIA_FENCE_GATE, BIRCH_FENCE_GATE, DARK_OAK_FENCE_GATE, FENCE_GATE, JUNGLE_FENCE_GATE, SPRUCE_FENCE_GATE -> FenceGate(block, settings)
      else -> null
    }
  }
}
