package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Set of door pairs.
 *
 * @author moltendorf
 */
class Set_Trap extends Set {
	protected Set_Trap(final Block block, final int data) {
		super (block.getLocation());

		primary = new Pair_Trap(block, location, data);
	}

	// Final data.
	private final Pair_Trap primary;

	@Override
	protected boolean apply(final boolean open, final Material material) {
		return primary.apply(open);
	}

	@Override
	protected boolean equals(final Set_Trap set) {
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
