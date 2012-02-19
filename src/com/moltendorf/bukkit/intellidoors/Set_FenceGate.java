package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Set of door pairs.
 *
 * @author moltendorf
 */
class Set_FenceGate extends Set {
	protected Set_FenceGate(final Block block, final int data) {
		super (block.getLocation());

		primary = new Pair_FenceGate(block, location, data);
	}

	// Final data.
	private final Pair_FenceGate primary;

	@Override
	protected boolean apply(final boolean open, final Material material) {
		return primary.apply(open);
	}

	@Override
	protected boolean equals(final Set_FenceGate set) {
		return primary.equals(set.primary);
	}

	@Override
	protected int getPower(final boolean open) {
		return open ? 1 : 0;
	}

	@Override
	protected boolean powered() {
		return primary.powered();
	}
}
