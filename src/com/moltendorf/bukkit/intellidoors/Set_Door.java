package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Set for door pair.
 *
 * @author moltendorf
 */
abstract class Set_Door extends Set {
	protected Set_Door(final Pair pair) {
		super (pair.location);

		primary = pair;
	}

	protected Set_Door(final Pair pair, final Location location) {
		super (pair.location, location);

		primary = pair;
	}

	// Final data.
	protected final Pair primary;

	@Override
	protected boolean apply(final boolean open, final Material material) {
		return primary.apply(open, material);
	}

	@Override
	protected int getPower(final boolean open) {
		return primary.getPower(open);
	}

	@Override
	protected boolean equals(final Set_Door_Double set, final Door door, final List list, final long current) {
		if (primary.equals(set.primary) || primary.equals(set.secondary)) {
			door.merge(set, list, current);
		}

		return false;
	}

	@Override
	protected boolean equals(final Set_Door_Single set, final Door door, final List list, final long current) {
		return primary.equals(set.primary);
	}

	@Override
	protected boolean powered() {
		return primary.powered();
	}
}
