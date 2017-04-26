package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import net.moltendorf.bukkit.intellidoors.settings.*
import org.bukkit.*
import org.bukkit.block.*

/**
 * Created by moltendorf on 16/5/10.
 *
 * Main door interface.
 */
interface DoorInterface {
  val facing : BlockFace
  val location : Location
  val powered : Bool
  val settings : Settings
  val type : Material

  var open : Bool

  var inverted get() = open != powered
    set(value) {
      open = value != powered
    }

  fun interactReset() {
    if (settings.interactReset) {
      resetIn(settings.interactResetTicks, inverted)
    }
  }

  fun onRedstone(onDoor : DoorInterface) : Bool {
    if (settings.redstone) {
      if (!inverted) {
        timer.cancel(this)

        return true
      }

      if (settings.redstoneReset && resetIn(settings.redstoneResetTicks, !powered)) {
        return true
      }

      open = powered

      if (update()) {
        playSound(open)
      }

      return true
    } else if (this is Sync) {
      onDoor.onRedstone(onDoor)
    }

    return false
  }

  fun reset() {
    if (inverted) {
      inverted = false

      if (update()) {
        playSound(false)
      }
    }
  }

  fun resetIn(delay : Long, state : Bool) : Bool {
    when {
      state -> timer.resetDoorIn(this, delay)
      else -> timer.cancel(this)
    }

    return state
  }

  fun toggle() {
    open = !open
  }

  fun playSound(open : Bool) = location.world.playSound(location, sound(open), 1f, 1f)

  fun onInteract(onDoor : DoorInterface) : Bool
  fun sound(open : Bool) : Sound
  fun update() : Bool
}
