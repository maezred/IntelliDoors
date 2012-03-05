package com.moltendorf.bukkit.intellidoors;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Listener register.
 *
 * @author moltendorf
 */
final class Listeners implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerInteractEventHandler(final PlayerInteractEvent event) {
		if (!event.hasBlock()) {
			return;
		}

		// Do stuff.
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void PlayerInteractEventMonitor(final PlayerInteractEvent event) {
		if (!event.hasBlock()) {
			return;
		}

		// Do stuff.
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockRedstoneEventMonitor(final BlockRedstoneEvent event) {
		// Do stuff.
	}
}
