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
      if (!inverted) {
        IntelliDoors.instance.timer.cancel(this)

        return true
      }

      if (settings.redstoneReset && resetIn(settings.redstoneResetTicks, !powered)) {
        return true
      }

      open = powered

      if (update()) {
        playSound(open)
      }

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

  fun reset() {
    if (inverted) {
      inverted = false

      if (update()) {
        playSound(false)
      }
    }
  }

  fun resetIn(delay: Long, state: Boolean): Boolean {
    val timer = IntelliDoors.instance.timer

    return if (state) {
      timer.resetDoorIn(this, delay)
      true
    } else {
      timer.cancel(this)
      false
    }
  }

  fun toggle() {
    open = !open
  }

  fun onInteract(onDoor: Door): Boolean
  fun sound(open: Boolean): Sound
  fun update(): Boolean

  interface Iron : Door {
    override fun onInteract(onDoor: Door): Boolean {
      if (settings.interact) {
        toggle()

        if (update()) {
          playSound(open)
        }

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
        toggle()

        if (onDoor !== this) {
          onDoor.toggle()
          update()
        }

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
      return if (open) Sound.BLOCK_WOODEN_DOOR_OPEN else Sound.BLOCK_WOODEN_DOOR_CLOSE
    }
  }

  interface Group // Marker type.
}
