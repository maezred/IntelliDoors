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

	protected Handler_Redstone_Double(final Material material, final Set_Door_Single set, final boolean state) {
		super (material, set, state);

		side = state;
	}

	protected Handler_Redstone_Double(final Set_Trap set, final boolean state) {
		super (set, state);

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

			door.reset();

			if (door.power()) {
				door.reset(Door.reset, time);
			}

			return door.getPower(side);
		}
	}
}
