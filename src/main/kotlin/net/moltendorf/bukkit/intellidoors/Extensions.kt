@file:Suppress("DEPRECATION")

package net.moltendorf.bukkit.intellidoors

import net.moltendorf.bukkit.intellidoors.settings.GlobalSettings
import org.bukkit.Server
import org.bukkit.block.Block
import org.bukkit.block.BlockState
import org.bukkit.configuration.Configuration
import org.bukkit.scheduler.BukkitScheduler
import java.util.logging.Logger

/**
 * Created by moltendorf on 2016-06-07.
 */
internal val enabled: Boolean
  get() = IntelliDoors.enabled

internal val instance: IntelliDoors
  get() = IntelliDoors.instance

internal val config: Configuration
  get() = instance.config

internal val log: Logger
  get() = instance.logger

internal val server: Server
  get() = instance.server

internal val scheduler: BukkitScheduler
  get() = server.scheduler

internal val settings: GlobalSettings
  get() = instance.settings

internal val timer: Timer
  get() = instance.timer

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
