package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import org.bukkit.Sound.*

/**
 * Created by moltendorf on 16/5/10.
 *
 * Code specific for iron types.
 */
interface IronType : DoorInterface {
  override fun onInteract(onDoor : DoorInterface) : Bool {
    if (settings.interact) {
      toggle()

      if (update()) {
        playSound(open)
      }

      interactReset()
    } else if (this is Sync) {
      return onDoor.onInteract(onDoor)
    }

    return false
  }

  override fun sound(open : Bool) = if (open) BLOCK_IRON_DOOR_OPEN else BLOCK_IRON_DOOR_CLOSE
}
