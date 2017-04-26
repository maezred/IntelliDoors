package net.moltendorf.bukkit.intellidoors.settings

import net.moltendorf.bukkit.intellidoors.*

data class Settings(
  var interact : Bool,
  var interactReset : Bool,
  var interactResetTicks : Long,
  var redstone : Bool,
  var redstoneReset : Bool,
  var redstoneResetTicks : Long
)
