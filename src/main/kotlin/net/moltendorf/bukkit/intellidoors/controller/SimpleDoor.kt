package net.moltendorf.bukkit.intellidoors.controller

import net.moltendorf.bukkit.intellidoors.intData
import net.moltendorf.bukkit.intellidoors.settings.Settings
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * Created by moltendorf on 16/5/2.
 */
abstract class SimpleDoor(val block: Block, settings: Settings) : BaseDoor(settings) {
  val state = block.state

  override val facing: BlockFace
    get() = FACING[state]

  override val location: Location
    get() = state.location

  override val powered: Boolean
    get() = block.isBlockIndirectlyPowered

  override val type: Material
    get() = state.type

  override var open = state.intData and 4 == 4

  override fun update(): Boolean {
    if (open) {
      state.apply(Flag.OPEN)
    } else {
      state.apply(Mask.CLOSED)
    }

    return state.update(false, false)
  }
}
