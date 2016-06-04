package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.IntelliDoors
import net.moltendorf.bukkit.intellidoors.settings.Settings
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 16/5/10.
 */
interface Door {
  val facing: BlockFace
  val location: Location
  val powered: Boolean
  val settings: Settings
  val type: Material

  var open: Boolean

  var inverted: Boolean
    get() = open != powered
    set(value) {
      open = value != powered
    }

  fun interactReset() {
    if (settings.interactReset) {
      resetIn(settings.interactResetTicks, inverted)
    }
  }

  fun onRedstone(onDoor: Door): Boolean {
    return if (settings.redstone) {
      if (settings.redstoneReset && resetIn(settings.redstoneResetTicks, !powered)) {
        return true
      }

      playSound(powered)
      open = powered
      true
    } else {
      if (this is Group) {
        onDoor.onRedstone(onDoor)
      }

      false
    }
  }

  fun playSound(open: Boolean) {
    location.world.playSound(location, sound(open), 1f, 1f)
  }

  fun resetIn(delay: Long, state: Boolean): Boolean {
    val timer = IntelliDoors.instance.timer

    return if (state) {
      timer.shutDoorIn(this, delay)
      true
    } else {
      timer.cancel(this)
      false
    }
  }

  fun toggle() {
    inverted = !inverted
  }

  fun onInteract(onDoor: Door): Boolean
  fun overrideOpen(value: Boolean)
  fun sound(open: Boolean): Sound

  interface Iron : Door {
    override fun onInteract(onDoor: Door): Boolean {
      if (settings.interact) {
        playSound(!open)
        toggle()
        interactReset()
      } else if (this is Group) {
        return onDoor.onInteract(onDoor)
      }

      return false
    }

    override fun sound(open: Boolean): Sound {
      return if (open) Sound.BLOCK_IRON_DOOR_OPEN else Sound.BLOCK_IRON_DOOR_CLOSE
    }
  }

  interface Wood : Door {
    override fun onInteract(onDoor: Door): Boolean {
      return if (settings.interact) {
        val isOpen = !open

        onDoor.overrideOpen(isOpen)
        open = isOpen
        interactReset()

        false
      } else {
        if (this is Group) {
          onDoor.onInteract(onDoor)
        } else {
          true // Prevent door from opening.
        }
      }
    }

    override fun sound(open: Boolean): Sound {
      return if (open) Sound.BLOCK_IRON_DOOR_OPEN else Sound.BLOCK_IRON_DOOR_CLOSE
    }
  }

  interface Group // Marker type.
}
