@file:Suppress("DEPRECATION")

package net.moltendorf.bukkit.intellidoors

import org.bukkit.block.*
import java.util.logging.Level.*

/**
 * Created by moltendorf on 2016-06-07.
 *
 * Extensions file that contains a bunch of useful stuff.
 */

// Welcome to Kotlin
internal typealias Bool = Boolean

// Package Variables
internal inline val enabled get() = Plugin.enabled
internal inline val instance get() = Plugin.instance

private inline val logger get() = instance.logger
private inline val scheduler get() = server.scheduler

internal inline val config get() = instance.config
internal inline val server get() = instance.server
internal inline val settings get() = instance.settings
internal inline val timer get() = instance.timer

// Messages

internal fun broadcast(s : String) = server.broadcastMessage(s)
internal fun console(s : String) = server.consoleSender.sendMessage("[${instance.name}] $s")

// Logging

internal fun trace(s : String, e : Throwable) = logger.log(SEVERE, s, e)
internal inline fun s(s : () -> Any) = logger.severe("${s()}")
internal inline fun w(s : () -> Any) = logger.warning("${s()}")
internal inline fun i(s : () -> Any) = logger.info("${s()}")
internal inline fun f(s : () -> Any) = logger.fine("${s()}")

// Tasks

internal fun runTask(s : () -> Unit) = scheduler.runTask(instance, s)
internal fun runTask(delay : Long, s : () -> Unit) = scheduler.runTaskLater(instance, s, delay)
internal fun runTask(period : Long, delay : Long = 0, s : () -> Unit) = scheduler.runTaskTimer(instance, s, delay, period)
internal fun runAsyncTask(s : () -> Unit) = scheduler.runTaskAsynchronously(instance, s)
internal fun runAsyncTask(delay : Long, s : () -> Unit) = scheduler.runTaskLaterAsynchronously(instance, s, delay)
internal fun runAsyncTask(period : Long, delay : Long = 0, s : () -> Unit) = scheduler.runTaskTimerAsynchronously(instance, s, delay, period)

// Extensions.
internal inline val <T : Any> T.i : T get() {
  i { this }

  return this
}

internal inline val <T : Any> T.f : T get() {
  f { this }

  return this
}

internal inline var Block.intData get() = data.toInt()
  set(byte) {
    data = byte.toByte()
  }

internal inline var BlockState.intData get() = rawData.toInt()
  set(byte) {
    rawData = byte.toByte()
  }
