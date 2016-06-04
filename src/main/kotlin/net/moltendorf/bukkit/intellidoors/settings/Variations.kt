package net.moltendorf.bukkit.intellidoors.settings

class Variations(val type: Type, val single: Settings, val pair: Settings) {
  enum class Type {
    DOOR, TRAP, GATE
  }
}
