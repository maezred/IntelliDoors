package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;

/**
 * Set of door pairs.
 *
 * @author moltendorf
 */
class Set_Door_Double extends Set_Door {
	protected Set_Door_Double(final Pair left, final Pair right) {
		super (left, right.location);

		secondary = right;
	}

	// Final data.
	protected final Pair secondary;

	@Override
	protected boolean apply(final boolean open, final Material material) {
		boolean sound = primary.apply(open, material);
		sound = secondary.apply(open, material) || sound;

		return sound;
	}

	@Override
	protected boolean apply(final boolean side, final boolean open, final Material material) {
		return side ? primary.apply(open, material) : secondary.apply(open, material);
	}

	@Override
	protected boolean equals(final Set_Door_Double set, final Door door, final List list, final long current) {
		if (primary.equals(set.primary)) {
			return true;
		} else if (primary.equals(set.secondary)) {
			door.split(set, secondary, list, current);
		} else if (secondary.equals(set.primary)) {
			door.split(set, primary, list, current);
		}

		return false;
	}

	@Override
	protected boolean equals(final Set_Door_Single set, final Door door, final List list, final long current) {
		if (primary.equals(set.primary)) {
			door.split(set, secondary, list, current);
		} else if (secondary.equals(set.primary)) {
			door.split(set, primary, list, current);
		}

		return false;
	}

	@Override
	protected boolean powered() {
		return primary.powered() || secondary.powered();
	}
}
