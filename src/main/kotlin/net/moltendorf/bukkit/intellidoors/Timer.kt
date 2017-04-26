package net.moltendorf.bukkit.intellidoors

import net.moltendorf.bukkit.intellidoors.controller.*
import org.bukkit.scheduler.*
import java.util.*

/**
 * Created by moltendorf on 16/5/24.
 */
class Timer {
  private val doors = HashMap<DoorInterface, BukkitTask>()

  fun resetDoorIn(door : DoorInterface, ticks : Long) {
    // Cancel any currently pending shut action.
    cancel(door)

    // (Re-)schedule the door to be shut.
    doors[door] = runTask(delay = ticks) {
      // This door is no longer pending to be shut.
      doors.remove(door)
      door.reset()
    }
  }

  fun cancel(door : DoorInterface) {
    doors.remove(door)?.cancel()
  }

  fun resetAllDoors() {
    for ((door, task) in doors) {
      task.cancel()
      door.reset()
    }

    doors.clear()
  }
}
