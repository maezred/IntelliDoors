package net.moltendorf.Bukkit.IntelliDoors.controller

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block

/**
 * Created by moltendorf on 15/05/23.
 */
class FenceGate(block: Block) : AbstractDoor(block) {
  override fun sound(open: Boolean): Sound {
    return when (type) {
      Material.ACACIA_FENCE_GATE,
      Material.BIRCH_FENCE_GATE,
      Material.DARK_OAK_FENCE_GATE,
      Material.FENCE_GATE,
      Material.JUNGLE_FENCE_GATE,
      Material.SPRUCE_FENCE_GATE -> {
        if (open) Sound.BLOCK_FENCE_GATE_OPEN else Sound.BLOCK_FENCE_GATE_CLOSE
      }
      else -> Sound.BLOCK_ANVIL_LAND
    }
  }

  companion object {
    operator fun get(block: Block): FenceGate? {
      return null
    }
  }
}
