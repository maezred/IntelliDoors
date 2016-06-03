package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.Settings
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block

/**
 * Created by moltendorf on 15/05/23.
 */
class TrapDoor private constructor(block: Block, settings: Settings.TypeSettings) : AbstractDoor(block, settings) {
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
    operator fun invoke(block: Block, settings: Settings.TypeSettings): TrapDoor? {
      return when (block.type) {
        Material.IRON_TRAPDOOR,
        Material.TRAP_DOOR -> TrapDoor(block, settings)
        else -> null
      }
    }
  }
}