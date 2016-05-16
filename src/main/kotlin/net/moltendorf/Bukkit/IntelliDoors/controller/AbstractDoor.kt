package net.moltendorf.Bukkit.IntelliDoors.controller

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * Created by moltendorf on 16/5/2.
 */
abstract class AbstractDoor(val block: Block) : Door() {
  override val location: Location
    get() {
      return block.location
    }

  override val type: Material
    get() {
      return block.type
    }

  protected var data: Int = block.data.toInt()

  override var open: Boolean
    get() {
      return data and 4 == 0
    }
    set(value) {
      if (open != value) {
        data += if (value) 4 else -4
        block.data = data.toByte()
      }
    }

  override fun overrideOpen(value: Boolean) {
    if (open != open) {
      data += if (open) 4 else -4
    }
  }
}
