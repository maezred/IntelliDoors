package net.moltendorf.Bukkit.IntelliDoors.controller;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class DoubleDoor implements Door {
  public static DoubleDoor getDoor(SingleDoor door) {
    boolean left = door.isLeft();
    BlockFace facing = door.getFacing();

    Block otherBlock;

    if (left) {
      otherBlock = door.top.getRelative(facing);
    } else {
      otherBlock = door.top.getRelative(facing, -1);
    }

    // Check if it's the same type and also the top of the door.
    if (otherBlock.getType() == door.top.getType() && (otherBlock.getData() & 8) == 8) {
      SingleDoor otherDoor = SingleDoor.getDoor(otherBlock);

      if (facing == otherDoor.getFacing() && left == otherDoor.isRight()) {
        if (left) {
          return new DoubleDoor(door, otherDoor, door.isClosed());
        } else {
          return new DoubleDoor(otherDoor, door, door.isClosed());
        }
      }
    }

    return null;
  }

  private SingleDoor left, right;

  private boolean closed;

  public DoubleDoor(SingleDoor left, SingleDoor right, boolean closed) {
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
  public Material getType() {
    return left.getType();
  }

  @Override
  public void wasToggled(Door onDoor) {
    left.wasToggled(onDoor);
    right.wasToggled(onDoor);
  }
}