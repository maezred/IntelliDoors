package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * Door pair.
 *
 * @author moltendorf
 */
class Pair_Trap extends BlockInfo {
	Pair_Trap(final Block block, final Location position, final int data) {
		rotation = GetRotation(data);

		this.block = block;

		// Get the location of the block.
		location = position;
	}

	// Final data.
	private final int rotation;
	private final Block block;
	private final Location location;

	protected boolean apply(final boolean open) {
		final int data = open ? rotation+4 : rotation;

		if (GetData(block) == data) {
			return false;
		}

		SetData(block, data, true);

		return true;
	}

	protected boolean equals(Pair_Trap pair) {
		return location.equals(pair.location) && rotation == pair.rotation;
	}

	protected boolean powered() {
		return IsPowered(block);
	}
}
