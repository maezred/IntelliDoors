package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;

/**
 * Door event handler class.
 *
 * @author moltendorf
 */
class Handler_Redstone_Double extends Handler {
	protected Handler_Redstone_Double(final Material material, final Set_Door_Double set, final boolean state) {
		super (material, set, state);

		side = state;
	}

	// Final data.
	private final boolean side;

	@Override
	protected int getPower() {
		synchronized (door) {
			if (door.busy()) {
				return door.getPower(side);
			}

			if (door.reset()) {
				door.reset(Door.reset, time);
			}

			return door.getPower(side);
		}
	}
}
