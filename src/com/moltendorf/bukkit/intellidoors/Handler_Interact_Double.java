package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;


/**
 * Door event handler class.
 *
 * @author moltendorf
 */
class Handler_Interact_Double extends Handler {
	protected Handler_Interact_Double(final Material material, final Set_Door_Double set, final boolean state, final boolean clicked) {
		super (material, set, state);

		open = state;
		side = clicked;
	}

	// Final data.
	private final boolean open;
	private final boolean side;

	@Override
	protected void onInteract() {
		synchronized (door) {
			door.set(!side, !open);

			door.reset(Door.delay, time);
			door.unlock();
		}
	}
}
