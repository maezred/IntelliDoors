package net.moltendorf.Bukkit.IntelliDoors.controller;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

/**
 * Created by moltendorf on 15/05/23.
 */
public class TrapDoor implements Door {
  public static Door getDoor(Block block) {
    return null;
  }

  private Material type;

  private Location location;

  protected Block block;

  protected byte data;

  public TrapDoor(Block block) {
    this.block = block;

    data = block.getData();
    location = block.getLocation();
    type = block.getType();
  }

  @Override
  public void open() {
    if (isClosed()) {
      data += 4;

      block.setData(data);
    }
  }

  @Override
  public void close() {
    if (isOpened()) {
      data -= 4;

      block.setData(data);
    }
  }

  @Override
  public void toggle() {
    data += isClosed() ? 4 : -4;

    block.setData(data);
  }

  @Override
  public boolean isOpened() {
    return (data & 4) == 4;
  }

  @Override
  public boolean isClosed() {
    return (data & 4) == 0;
  }

  @Override
  public Material getType() {
    return type;
  }

  @Override
  public void wasToggled(Door onDoor) {
    if (type == Material.IRON_TRAPDOOR) {
      if (isClosed()) {
        open();

        location.getWorld().playSound(location, Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1, 1);
      } else {
        close();

        location.getWorld().playSound(location, Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 1, 1);
      }
    }
  }
}
