package net.moltendorf.Bukkit.IntelliDoors

import net.moltendorf.Bukkit.IntelliDoors.controller.Door
import org.bukkit.scheduler.BukkitTask
import java.util.*

/**
 * Created by moltendorf on 16/5/24.
 */
class Timer {
  private val doors = HashMap<Door, BukkitTask>();

  fun shutDoorIn(door: Door, ticks: Long) {
    val instance = IntelliDoors.instance

    // Cancel any currently pending shut action.
    doors[door]?.cancel()

    // (Re-)schedule the door to be shut.
    val task = instance.server.scheduler.runTaskLater(instance, {
      // This door is no longer pending to be shut.
      doors.remove(door)

      // Shut the door!
      door.open = false
    }, ticks)

    // This door is pending to be shut.
    doors[door] = task
  }
}
