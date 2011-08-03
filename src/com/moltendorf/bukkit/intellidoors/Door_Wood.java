package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;

/**
 * Door class for Iron Doors.
 *
 * @author moltendorf
 */
class Door_Wood extends Door {
	// Constants.
	private static final List list = new List();
	private static final Material material = Material.WOODEN_DOOR;

	private Door_Wood(final Set set, final boolean open, final long current) {
		super (set, open, current);
	}

	private Door_Wood(final Set set, final boolean open) {
		super (set, open);
	}

	protected static void Destruct() {
		list.destruct();
	}

	protected static Door Get(final Set_Door_Double set, final boolean open, final long current) {
		final Door door = list.get(set, current);

		if (door == null) {
			return new Door_Wood(set, open).push();
		}

		return door;
	}

	protected static Door Get(final Set_Door_Single set, final boolean open, final long current) {
		final Door door = list.get(set, current);

		if (door == null) {
			return new Door_Wood(set, open).push();
		}

		return door;
	}

	@Override
	protected boolean apply(final boolean open) {
		return set.apply(open, material);
	}

	@Override
	protected boolean apply(final boolean side, final boolean open) {
		return set.apply(side, open, material);
	}

	@Override
	protected Door make(final Set set) {
		return new Door_Wood(set, open, time);
	}

	private Door push() {
		list.push(this);

		return this;
	}

	@Override
	public void run() {
		run(list);
	}

	@Override
	public void splice() {
		list.splice(this);
	}
}
