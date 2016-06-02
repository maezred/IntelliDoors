package net.moltendorf.bukkit.intellidoors

import java.util.*

class CanonicalSet<T> {
  private val map = HashMap<T, T>()

  operator fun get(key: T): T {
    val cached = map[key]

    return if (cached == null) {
      map[key] = key
      key
    } else {
      cached
    }
  }
}
