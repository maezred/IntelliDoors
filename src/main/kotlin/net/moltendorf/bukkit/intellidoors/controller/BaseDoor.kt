package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.Settings
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 2016-06-03.
 */

abstract class BaseDoor(override val settings: Settings.TypeSettings) : Door {
  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }

    if (other?.javaClass != javaClass) {
      return false
    }

    other as Door

    if (location != other.location) {
      return false
    }

    return true
  }

  override fun hashCode(): Int {
    return location.hashCode()
  }

  companion object {
    val FACING = arrayOf(BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST)
  }
}
