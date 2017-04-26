package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import net.moltendorf.bukkit.intellidoors.controller.Door.Flag.*
import net.moltendorf.bukkit.intellidoors.settings.*
import org.bukkit.block.*
import org.bukkit.block.BlockFace.*

/**
 * Created by moltendorf on 2016-06-03.
 *
 * Boiler plate class that provides base logic usable by all doors.
 */
abstract class Door(override val settings : Settings) : DoorInterface {
  override fun equals(other : Any?) : Bool {
    if (this === other) {
      return true
    }

    if (other?.javaClass != javaClass) {
      return false
    }

    other as DoorInterface

    if (location != other.location) {
      return false
    }

    return true
  }

  override fun hashCode() = location.hashCode()

  companion object {
    val faces = listOf(NORTH, EAST, SOUTH, WEST, NORTH, EAST, SOUTH)
    val lookup = mapOf(Pair(NORTH, 0), Pair(EAST, 1), Pair(SOUTH, 2), Pair(WEST, 3))

    fun rotate(value : BlockFace, amount : Int) = faces[lookup[value]!! + amount]
  }

  enum class Flag(val value : Int) : (BlockState) -> Unit {
    OPEN(4),
    TOP(8);

    override fun invoke(p1 : BlockState) {
      p1.intData = p1.intData or value
    }
  }

  enum class Mask(flag : Flag) : (BlockState) -> Unit {
    CLOSED(OPEN),
    BOTTOM(TOP);

    val value = flag.value.inv()

    override fun invoke(p1 : BlockState) {
      p1.intData = p1.intData and value
    }
  }
}
