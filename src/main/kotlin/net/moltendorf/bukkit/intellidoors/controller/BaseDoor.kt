package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.intData
import net.moltendorf.bukkit.intellidoors.settings.Settings
import org.bukkit.block.BlockFace
import org.bukkit.block.BlockState

/**
 * Created by moltendorf on 2016-06-03.
 */

abstract class BaseDoor(override val settings: Settings) : Door {
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
    val faces = listOf(
      BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH
    )

    val lookup = mapOf(Pair(BlockFace.NORTH, 0), Pair(BlockFace.EAST, 1), Pair(BlockFace.SOUTH, 2), Pair(BlockFace.WEST, 3))

    fun rotate(value: BlockFace, amount: Int): BlockFace {
      return faces[lookup[value]!! + amount]
    }
  }

  enum class Flag(val value: Int) : (BlockState) -> Unit {
    OPEN(4),
    TOP(8);

    override fun invoke(p1: BlockState) {
      p1.intData = p1.intData or value
    }
  }

  enum class Mask(val value: Int) : (BlockState) -> Unit {
    CLOSED(Flag.OPEN),
    BOTTOM(Flag.TOP);

    constructor(flag: Flag) : this(flag.value.inv())

    override fun invoke(p1: BlockState) {
      p1.intData = p1.intData and value
    }
  }
}
