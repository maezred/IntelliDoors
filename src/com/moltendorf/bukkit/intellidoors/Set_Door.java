package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Location;

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
	protected boolean apply(final boolean open) {
		return primary.apply(open);
	}

	@Override
	protected int getPower(final boolean open) {
		return primary.getPower(open);
	}

	@Override
	protected boolean equals(final Set_Door_Double set, final Door door, final List list) {
		if (primary.equals(set.primary) || primary.equals(set.secondary)) {
			door.merge(set, list);
		}

		return false;
	}

	@Override
	protected boolean equals(final Set_Door_Single set, final Door door, final List list) {
		return primary.equals(set.primary);
	}

	@Override
	protected boolean powered() {
		return primary.powered();
	}
}
