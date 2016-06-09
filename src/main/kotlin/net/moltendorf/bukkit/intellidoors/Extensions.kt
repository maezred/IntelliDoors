@file:Suppress("DEPRECATION")

package net.moltendorf.bukkit.intellidoors

import org.bukkit.block.Block
import org.bukkit.block.BlockState

/**
 * Created by moltendorf on 2016-06-07.
 */
internal var Block.intData: Int
  get() = data.toInt()
  set(byte: Int) {
    data = byte.toByte()
  }

internal var BlockState.intData: Int
  get() = rawData.toInt()
  set(byte: Int) {
    rawData = byte.toByte()
  }
