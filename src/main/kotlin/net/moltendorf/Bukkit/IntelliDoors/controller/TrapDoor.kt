package net.moltendorf.Bukkit.IntelliDoors.controller

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block

/**
 * Created by moltendorf on 15/05/23.
 */
class TrapDoor(block: Block) : AbstractDoor(block) {
  override fun sound(open: Boolean): Sound {
    return when (type) {
      Material.IRON_TRAPDOOR -> {
        if (open) Sound.BLOCK_IRON_TRAPDOOR_OPEN else Sound.BLOCK_IRON_TRAPDOOR_CLOSE
      }
      Material.TRAP_DOOR -> {
        if (open) Sound.BLOCK_WOODEN_TRAPDOOR_OPEN else Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE
      }
      else -> Sound.BLOCK_ANVIL_LAND
    }
  }

  companion object {
    operator fun get(block: Block): TrapDoor? {
      return when (block.type) {
        Material.IRON_TRAPDOOR,
        Material.TRAP_DOOR -> TrapDoor(block)
        else -> null
      }
    }
  }
}
