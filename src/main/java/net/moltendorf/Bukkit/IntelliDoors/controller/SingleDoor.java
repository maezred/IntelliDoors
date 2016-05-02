package net.moltendorf.Bukkit.IntelliDoors.controller;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class SingleDoor implements Door {
  public static SingleDoor getDoor(Block block) {
    // org.bukkit.material.SingleDoor: Deprecated? Is this really so hard? Sigh.
    byte data = block.getData();

    Block top, bottom;

    if ((data & 8) == 8) {
      // Top of door.
      top = block;
      bottom = block.getRelative(BlockFace.DOWN);
    } else {
      // Bottom of door.
      top = block.getRelative(BlockFace.UP);
      bottom = block;
    }

    if (top.getType() == bottom.getType()) {
      return new SingleDoor(top, bottom);
    }

    return null;
  }

  private Material type;

  private Location location;

  protected Block top, bottom;

  protected byte topData, bottomData;

  public SingleDoor(Block top, Block bottom) {
    type = top.getType();
    location = top.getLocation();

    this.top = top;
    this.bottom = bottom;

    topData = top.getData();
    bottomData = bottom.getData();
  }

  private static BlockFace[] Facing = {BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST};

  public BlockFace getFacing() {
    return Facing[bottomData & 3];
  }

  public boolean isLeft() {
    return (topData & 1) == 0;
  }

  public boolean isRight() {
    return (topData & 1) == 1;
  }

  @Override
  public boolean isClosed() {
    return (bottomData & 4) == 0;
  }

  @Override
  public boolean isOpened() {
    return (bottomData & 4) == 4;
  }

  @Override
  public void toggle() {
    bottomData += isClosed() ? 4 : -4;

    bottom.setData(bottomData);
  }

  @Override
  public void close() {
    if (isOpened()) {
      bottomData -= 4;

      bottom.setData(bottomData);
    }
  }

  @Override
  public void open() {
    if (isClosed()) {
      bottomData += 4;

      bottom.setData(bottomData);
    }
  }

  @Override
  public Material getType() {
    return type;
  }

  @Override
  public void wasToggled(Door onDoor) {
    if (type == Material.IRON_DOOR_BLOCK) {
      if (isClosed()) {
        open();

        if (this == onDoor) {
          location.getWorld().playSound(location, Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
        }
      } else {
        close();

        if (this == onDoor) {
          location.getWorld().playSound(location, Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
        }
      }
    } else if (this != onDoor) {
      toggle();
    }
  }
}
