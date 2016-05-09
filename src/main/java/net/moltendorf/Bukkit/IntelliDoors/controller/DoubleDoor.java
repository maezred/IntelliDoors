package net.moltendorf.Bukkit.IntelliDoors.controller;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class DoubleDoor extends AbstractDoor {
  public static DoubleDoor getDoor(SingleDoor door) {
    boolean left = door.isLeft();
    BlockFace facing = door.getFacing();

    Block otherBlock;

    if (left) {
      otherBlock = door.getTop().getRelative(facing);
    } else {
      otherBlock = door.getTop().getRelative(facing, -1);
    }

    // Check if it's the same type and also the top of the door.
    if (otherBlock.getType() == door.getTop().getType() && (otherBlock.getData() & 8) == 8) {
      SingleDoor otherDoor = SingleDoor.Companion.get(otherBlock);

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

  private Location location;

  public DoubleDoor(SingleDoor left, SingleDoor right, boolean closed) {
    this.left = left;
    this.right = right;

    this.closed = closed;

    location = left.getLocation().subtract(right.getLocation()).add(left.getLocation());
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
    onDoor.overrideState(isOpened());
    super.wasToggled(onDoor);
  }

  @Override
  public void overrideState(boolean closed) {
    left.overrideState(closed);
    right.overrideState(closed);
  }

  @Override
  protected Location getLocation() {
    return location;
  }
}
