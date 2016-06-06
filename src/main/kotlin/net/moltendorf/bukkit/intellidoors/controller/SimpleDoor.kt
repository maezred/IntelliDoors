package net.moltendorf.bukkit.intellidoors.controller

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
    get() = FACING[data and 3]

  override val location: Location
    get() = state.location

  override val powered: Boolean
    get() = block.isBlockIndirectlyPowered

  override val type: Material
    get() = state.type

  var data: Int = state.rawData.toInt()
    protected set

  override var open: Boolean
    get() {
      return data and 4 == 4
    }
    set(value) {
      if (open != value) {
        data += if (value) 4 else -4
        state.rawData = data.toByte()
        state.update(false, false)
      }
    }

  override fun overrideOpen(value: Boolean) {
    if (open != value) {
      data += if (value) 4 else -4
    }
  }
}
