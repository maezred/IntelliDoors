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
	protected int getPower() {
		return open ? 0 : 1;
	}

	@Override
	protected void reset() {
		if (!set.powered()) {
			busy = true;
			power = true;

			open = true;

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
	protected synchronized void run(final List list) {
		busy = true;

		open = set.powered();

		if (apply(open)) {
			set.sound();
		}

		list.splice(this);
	}
}
