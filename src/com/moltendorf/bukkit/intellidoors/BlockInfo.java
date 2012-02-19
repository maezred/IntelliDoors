package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Handles all static info retrieval for doors.
 *
 * @author moltendorf
 */
abstract class BlockInfo {
	// Constants.
	private final static BlockFace facing[] = {BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST};
	private final static int ignored[] = {0, 6, 8, 9, 10, 11, 27, 28, 30, 31, 32, 34, 36, 37, 38, 39, 40, 50, 51, 55, 59, 63, 64, 65, 66, 68, 69, 70, 71, 72, 75, 76, 77, 78, 83, 90, 93, 94, 96, 104, 105, 106, 107, 111, 115, 117, 119};

	private static boolean HasBit(final int data, final int bit) {
		return (data & bit) == bit;
	}

	private static boolean IsDoor(final Material material, final Block block) {
		return material == block.getType();
	}

	protected static boolean IsNotDoor(final Material material, final Block block) {
		return material != block.getType();
	}

	protected static int GetData(final Block block) {
		return block.getData();
	}

	protected static void SetData(final Block block, final int data, final boolean physics) {
		block.setData((byte) data, physics);
	}

	protected static boolean IsOpen(final int data) {
		return HasBit(data, 4);
	}

	private static boolean IsClosed(final int data) {
		return !IsOpen(data);
	}

	protected static boolean IsTop(final int data) {
		return HasBit(data, 8);
	}

	protected static boolean IsBottom(final int data) {
		return !IsTop(data);
	}

	protected static Block GetTop(final Block block) {
		return block.getRelative(BlockFace.UP);
	}

	protected static Block GetBottom(final Block block) {
		return block.getRelative(BlockFace.DOWN);
	}

	protected static int GetRotation(final int data) {
		return IsOpen(data) ? data-4 : data;
	}

	private static Block GetRelative(final Block block, int data, final int modifier) {
		if ((data += modifier) >= 4) {
			data -= 4;
		}

		return block.getRelative(facing[data]);
	}

	protected static Block GetFront(final Block block, final int data) {
		return GetRelative(block, data, 0);
	}

	protected static Block GetRight(final Block block, final int data) {
		return GetRelative(block, data, 1);
	}

	private static Block GetBack(final Block block, final int data) {
		return GetRelative(block, data, 2);
	}

	private static Block GetLeft(final Block block, final int data) {
		return GetRelative(block, data, 3);
	}

	private static boolean IsIgnored(final Block block) {
		final int id = block.getTypeId();

		for (int i = 0; i < ignored.length; ++i) {
			if (id == ignored[i]) {
				return true;
			}
		}

		return false;
	}

	protected static boolean IsForward(final Block block, final int rotation) {
		final Block back = GetBack(block, rotation);
		final Block left = GetLeft(block, rotation);

		if (IsIgnored(back)) {
			if (IsIgnored(GetBottom(back)) || !IsIgnored(left) || !IsIgnored(GetBottom(left))) {
				return true;
			}
		} else if (IsIgnored(GetBottom(back)) && !IsIgnored(left) && !IsIgnored(GetBottom(left))) {
			return true;
		}

		return false;
	}

	protected static boolean IsReverse(final Block block, final int rotation) {
		final Block back = GetBack(block, rotation);
		final Block left = GetLeft(block, rotation);

		if (IsIgnored(left)) {
			if (IsIgnored(GetBottom(left)) || !IsIgnored(back) || !IsIgnored(GetBottom(back))) {
				return true;
			}
		} else if (IsIgnored(GetBottom(left)) && !IsIgnored(back) && !IsIgnored(GetBottom(back))) {
			return true;
		}

		return false;
	}

	protected static boolean IsPowered(final Block block) {
		return block.getBlockPower() > 0;
	}

	protected static boolean VerifySet(final int primary, final int secondary) {
		return HasBit(secondary, 3) ? primary == secondary-3 : primary == secondary+1;
	}
}
