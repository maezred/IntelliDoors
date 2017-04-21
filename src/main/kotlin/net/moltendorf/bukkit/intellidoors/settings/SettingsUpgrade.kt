package net.moltendorf.bukkit.intellidoors.settings

import net.moltendorf.bukkit.intellidoors.log

private const val SETTINGS_VERSION = 5

fun upgradeSettings(settings: GlobalSettings) {
  if (settings.version < SETTINGS_VERSION) {
    var versions = 0

    for (i in settings.version + 1..SETTINGS_VERSION) {
      upgrade[i]?.invoke(settings) ?: continue

      ++versions
    }

    settings.version = SETTINGS_VERSION

    if (versions > 0) {
      val plural = if (versions > 1) "s" else ""

      log.warning("Your config is $versions version$plural out of date. New values have been inferred.")
      log.warning("It is recommended to back up your config and delete it so the latest can be generated.")
    }
  }
}

val upgrade = mapOf(
  Pair(5, fun(settings: GlobalSettings) {
    val ironTrapdoor = settings["iron-trapdoor"] ?: return

    // The settings loader needs refactoring badly so we'll just hack this in.
    ironTrapdoor.pair.interact = ironTrapdoor.single.interact
  })
)
