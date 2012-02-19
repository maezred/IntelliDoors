package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * Door pair.
 *
 * @author moltendorf
 */
class Pair_FenceGate extends BlockInfo {
	Pair_FenceGate(final Block block, final Location position, final int data) {
		rotation = data & 1;

		this.block = block;

		// Get the location of the block.
		location = position;
	}

	// Final data.
	private final int rotation;
	private final Block block;
	private final Location location;

	protected boolean apply(final boolean open) {
		final int orientation = GetRotation(block.getData());
		final int data = open ? (orientation+4) : (orientation);

		if (GetData(block) == data) {
			return false;
		}

		SetData(block, data, true);

		return true;
	}

	protected boolean equals(Pair_FenceGate pair) {
		return location.equals(pair.location) && (pair.rotation & 1) == rotation;
	}

	protected boolean powered() {
		return IsPowered(block);
	}
}
