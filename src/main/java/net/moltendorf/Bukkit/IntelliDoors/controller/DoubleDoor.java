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
public class DoubleDoor implements Door {
  public static Door Get(final SingleDoor door) {
    final boolean left = door.isLeft();
    final BlockFace facing = door.getFacing();

    final Block otherBlock;

    if (left) {
      otherBlock = door.top.getRelative(facing);
    } else {
      otherBlock = door.top.getRelative(facing, -1);
    }

    // Check if it's the same type and also the top of the door.
    if (otherBlock.getType() == door.top.getType() && (otherBlock.getData() & 8) == 8) {
      final SingleDoor otherDoor = new SingleDoor(otherBlock);

      if (facing == otherDoor.getFacing() && left == otherDoor.isRight()) {
        if (left) {
          return new DoubleDoor(door, otherDoor, door.isClosed());
        } else {
          return new DoubleDoor(otherDoor, door, door.isClosed());
        }
      }
    }

    return door;
  }

  final public SingleDoor left, right;

  private boolean closed;

  public DoubleDoor(final SingleDoor left, final SingleDoor right, final boolean closed) {
    this.left = left;
    this.right = right;

    this.closed = closed;
  }

  @Override
  public boolean isClosed() {
    return closed;
  }

  @Override
  public boolean isOpened() {
    return !closed;
  }

  @Override
  public void close() {
    left.close();
    right.close();
  }

  @Override
  public void open() {
    left.open();
    right.open();
  }

  @Override
  public void toggle() {
    left.toggle();
    right.toggle();
  }

  @Override
  public void wasToggled(SingleDoor onDoor) {
    if (onDoor.bottom.getType() != Material.IRON_DOOR_BLOCK) {
      // Invert door open state.
      onDoor.bottomData += onDoor.isClosed() ? 4 : -4;
    } else {
      Location location = onDoor.bottom.getLocation();

      if (closed) {
        location.getWorld().playSound(location, Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
      } else {
        location.getWorld().playSound(location, Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
      }
    }

    if (closed) {
      open();
    } else {
      close();
    }
  }
}
