package com.moltendorf.bukkit.intellidoors;

/**
 * Set of door pairs.
 *
 * @author moltendorf
 */
class Set_Door_Double extends Set_Door {
	protected Set_Door_Double(final Pair left, final Pair right) {
		super (left, right.location);

		secondary = right;
		secondary.forward = false;
	}

	// Final data.
	protected final Pair secondary;

	@Override
	protected boolean apply(final boolean open) {
		boolean sound = primary.apply(open);
		sound = secondary.apply(open) || sound;

		return sound;
	}

	@Override
	protected boolean equals(final Set_Door_Double set, final Door door, final List list) {
		if (primary.equals(set.primary)) {
			return true;
		} else if (primary.equals(set.secondary)) {
			door.split(set, secondary, list);
		} else if (secondary.equals(set.primary)) {
			door.split(set, primary, list);
		}

		return false;
	}

	@Override
	protected boolean equals(final Set_Door_Single set, final Door door, final List list) {
		if (primary.equals(set.primary)) {
			door.split(set, secondary, list);
		} else if (secondary.equals(set.primary)) {
			door.split(set, primary, list);
		}

		return false;
	}

	@Override
	protected boolean powered() {
		return primary.powered() || secondary.powered();
	}
}
