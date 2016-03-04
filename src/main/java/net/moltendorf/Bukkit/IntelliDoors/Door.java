package net.moltendorf.Bukkit.IntelliDoors;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class Door extends DoorType {
	final protected Block top, bottom;

	protected byte topData, bottomData;

	public Door(final Block block) {
		// org.bukkit.material.Door: Deprecated? Is this really so hard? Sigh.
		final byte data = block.getData();

		if ((data & 8) == 8) {
			// Top of door.
			top = block;
			bottom = block.getRelative(BlockFace.DOWN);

			topData = data;
			bottomData = bottom.getData();
		} else {
			// Bottom of door.
			top = block.getRelative(BlockFace.UP);
			bottom = block;

			topData = top.getData();
			bottomData = data;
		}
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

	public boolean isClosed() {
		return (bottomData & 4) == 0;
	}

	public boolean isOpened() {
		return (bottomData & 4) == 4;
	}

	public void close() {
		if (isOpened()) {
			bottomData -= 4;

			bottom.setData(bottomData);
		}
	}

	public void open() {
		if (isClosed()) {
			bottomData += 4;

			bottom.setData(bottomData);
		}
	}
}
