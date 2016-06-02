package net.moltendorf.bukkit.intellidoors

import net.moltendorf.bukkit.intellidoors.controller.Door
import org.bukkit.scheduler.BukkitTask
import java.util.*

/**
 * Created by moltendorf on 16/5/24.
 */
class Timer {
  private val doors = HashMap<Door, BukkitTask>();

  fun shutDoorIn(door: Door, ticks: Long) {
    // Cancel any currently pending shut action.
    cancel(door)

    // (Re-)schedule the door to be shut.
    val instance = IntelliDoors.instance
    doors[door] = instance.server.scheduler.runTaskLater(instance, {
      // This door is no longer pending to be shut.
      doors.remove(door)

      // Shut the door!
      door.location.world.playSound(door.location, door.sound(false), 1f, 1f)
      door.open = false
    }, ticks)
  }

  fun cancel(door: Door) {
    doors.remove(door)?.cancel()
  }

  fun shutAllDoors() {
    for ((door, task) in doors) {
      door.location.world.playSound(door.location, door.sound(false), 1f, 1f)
      door.open = false
      task.cancel()
    }

    doors.clear()
  }
}
