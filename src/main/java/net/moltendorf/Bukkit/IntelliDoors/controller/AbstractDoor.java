package net.moltendorf.Bukkit.IntelliDoors.controller;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

/**
 * Created by moltendorf on 16/5/2.
 */
public abstract class AbstractDoor implements Door {
  protected abstract Location getLocation();

  @Override
  public void wasToggled(Door onDoor) {
    if (getType() == Material.IRON_DOOR_BLOCK) {
      Location location = getLocation();

      if (isClosed()) {
        open();

        location.getWorld().playSound(location, Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
      } else {
        close();

        location.getWorld().playSound(location, Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
      }
    }
  }
}
