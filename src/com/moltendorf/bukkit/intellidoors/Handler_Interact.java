package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;

/**
 * Door event handler class.
 *
 * @author moltendorf
 */
class Handler_Interact extends Handler {
	protected Handler_Interact(final Material material, final Set_Door_Double set, final boolean state) {
		super (material, set, state);

		open = state;
	}

	protected Handler_Interact(final Material material, final Set_Door_Single set, final boolean state) {
		super (material, set, state);

		open = state;
	}

	protected Handler_Interact(final Set_Trap set, final boolean state) {
		super (set, state);

		open = state;
	}

	// Final data.
	private final boolean open;

	@Override
	protected void onInteract() {
		synchronized (door) {
			if (door.busy(time)) {
				return;
			}

			door.apply(!open);

			door.reset(Door.delay, time);
		}
	}
}
