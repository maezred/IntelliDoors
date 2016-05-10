package net.moltendorf.Bukkit.IntelliDoors.controller

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * Created by moltendorf on 15/05/23.

 * @author moltendorf
 */
class DoubleDoor(val left: SingleDoor, val right: SingleDoor, val closed: Boolean) : AbstractDoor() {

  private val location: Location

  init {
    location = left.getLocation().subtract(right.getLocation()).add(left.getLocation())
  }

  override fun isClosed(): Boolean {
    return closed
  }

  override fun isOpened(): Boolean {
    return !closed
  }

  override fun close() {
    left.close()
    right.close()
  }

  override fun open() {
    left.open()
    right.open()
  }

  override fun toggle() {
    left.toggle()
    right.toggle()
  }

  override fun getType(): Material {
    return left.type
  }

  override fun wasToggled(onDoor: Door) {
    onDoor.overrideState(isOpened)
    super.wasToggled(onDoor)
  }

  override fun overrideState(closed: Boolean) {
    left.overrideState(closed)
    right.overrideState(closed)
  }

  override fun getLocation(): Location {
    return location
  }

  companion object {
    operator fun get(door: SingleDoor): DoubleDoor? {
      val left = door.isLeft
      val facing = door.facing

      val otherBlock: Block

      if (left) {
        otherBlock = door.top.getRelative(facing)
      } else {
        otherBlock = door.top.getRelative(facing, -1)
      }

      // Check if it's the same type and also the top of the door.
      if (otherBlock.type == door.top.type && otherBlock.data.toInt() and 8 == 8) {
        val otherDoor = SingleDoor[otherBlock]

        if (otherDoor != null && facing == otherDoor.facing && left == otherDoor.isRight) {
          if (left) {
            return DoubleDoor(door, otherDoor, door.isClosed)
          } else {
            return DoubleDoor(otherDoor, door, door.isClosed)
          }
        }
      }

      return null
    }
  }
}
