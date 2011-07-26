package com.moltendorf.bukkit.intellidoors;

/**
 * Door class for Iron Doors.
 *
 * @author moltendorf
 */
class Door_Iron_Single extends Door_Iron {
	protected Door_Iron_Single(final Set set, final boolean open, final long current) {
		super (set, open, current);
	}

	protected Door_Iron_Single(final Set set, final boolean open) {
		super (set, open);
	}

	@Override
	protected void reset() {
		if (power && !set.powered()) {
			busy = true;

			open = true;
			power = false;

			if (apply(open)) {
				set.sound();
			}

			busy = false;
		}
	}

	@Override
	protected Door make(final Set set) {
		return new Door_Iron_Single(set, open, time);
	}

	@Override
	protected boolean power() {
		return open != power;
	}

	@Override
	protected synchronized void run(final List list) {
		if (task != -1) {
			task = -1;
			busy = true;

			open = !set.powered();
			power = !open;

			if (apply(open)) {
				set.sound();
			}

			list.splice(this);
		}
	}
}
