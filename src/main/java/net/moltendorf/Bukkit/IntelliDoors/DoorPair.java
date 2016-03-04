package net.moltendorf.Bukkit.IntelliDoors;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class DoorPair extends DoorType {
	public static DoorType Get(final Door door) {
		final boolean   left   = door.isLeft();
		final BlockFace facing = door.getFacing();

		final Block otherBlock;

		if (left) {
			otherBlock = door.top.getRelative(facing);
		} else {
			otherBlock = door.top.getRelative(facing, -1);
		}

		// Check if it's the same type and also the top of the door.
		if (otherBlock.getType() == door.top.getType() && (otherBlock.getData() & 8) == 8) {
			final Door otherDoor = new Door(otherBlock);

			if (facing == otherDoor.getFacing() && left == otherDoor.isRight()) {
				if (left) {
					return new DoorPair(door, otherDoor, door.isClosed());
				} else {
					return new DoorPair(otherDoor, door, door.isClosed());
				}
			}
		}

		return door;
	}

	final public Door left, right;

	private boolean closed;

	public DoorPair(final Door left, final Door right, final boolean closed) {
		this.left = left;
		this.right = right;

		this.closed = closed;
	}

	public boolean isClosed() {
		return closed;
	}

	public boolean isOpened() {
		return !closed;
	}

	public void close() {
		left.close();
		right.close();
	}

	public void open() {
		left.open();
		right.open();
	}
}
