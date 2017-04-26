package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.*
import net.moltendorf.bukkit.intellidoors.controller.Door.Flag.*
import net.moltendorf.bukkit.intellidoors.controller.Door.Mask.*
import net.moltendorf.bukkit.intellidoors.settings.*
import org.bukkit.block.*

/**
 * Created by moltendorf on 16/5/2.
 */
abstract class SimpleDoor(val block : Block, settings : Settings) : Door(settings) {
  val state = block.state!!

  override val location get() = state.location!!
  override val powered get() = block.isBlockIndirectlyPowered
  override val type get() = state.type!!

  override var open = state.intData and 4 == 4

  override fun update() : Bool {
    when {
      open -> state.apply(OPEN)
      else -> state.apply(CLOSED)
    }

    return state.update(false, false)
  }
}
