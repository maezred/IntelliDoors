package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import org.bukkit.*
import org.bukkit.Sound.*

/**
 * Created by moltendorf on 16/5/10.
 *
 * Code specific for wood types.
 */
interface WoodType : DoorInterface {
  override fun onInteract(onDoor : DoorInterface) : Bool {
    return when {
      settings.interact -> {
        toggle()

        if (onDoor !== this) {
          onDoor.toggle()
          update()
        }

        interactReset()

        false // Let door respond normally.
      }
      this is Sync -> onDoor.onInteract(onDoor)
      else -> true // Prevent door from opening.
    }
  }

  override fun sound(open : Bool) : Sound {
    return if (open) BLOCK_WOODEN_DOOR_OPEN else BLOCK_WOODEN_DOOR_CLOSE
  }
}
