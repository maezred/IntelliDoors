package net.moltendorf.Bukkit.IntelliDoors.controller

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound

/**
 * Created by moltendorf on 16/5/10.
 */
interface Door {
  val location: Location
  val type: Material

  var open: Boolean

  fun onInteract(onDoor: Door) {
    val isOpen = !open

    when (type) {
      Material.IRON_DOOR_BLOCK, Material.IRON_TRAPDOOR -> {
        location.world.playSound(location, sound(isOpen), 1f, 1f)

        open = isOpen
      }
    }
  }

  fun toggle() {
    open = !open
  }

  fun overrideOpen(value: Boolean)
  fun sound(open: Boolean): Sound
}
