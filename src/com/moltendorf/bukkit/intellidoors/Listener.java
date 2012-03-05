package com.moltendorf.bukkit.intellidoors;

import java.util.HashMap;
import org.bukkit.block.Block;
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
class Listeners implements Listener {
	final protected HashMap<Integer, Configuration.Door> doors = Plugin.configuration.doors;

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void PlayerInteractEventHandler(final PlayerInteractEvent event) {
		// Do we have a block?
		if (!event.hasBlock()) {
			return;
		}

		// Get our block and type.
		final Block block = event.getClickedBlock();
		final int type = block.getTypeId();

		final Configuration.Door configuration = doors.get(type);

		// Do we have configuration data for this?
		if (configuration == null) {
			return;
		}

		// Is this type of door enabled?
		if (configuration.enabled) {
			// Do stuff.
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void PlayerInteractEventMonitor(final PlayerInteractEvent event) {
		// Do we have a block?
		if (!event.hasBlock()) {
			return;
		}

		// Get our block and type.
		final Block block = event.getClickedBlock();
		final int type = block.getTypeId();

		final Configuration.Door configuration = doors.get(type);

		// Do we have configuration data for this?
		if (configuration == null) {
			return;
		}

		// Is this type of door enabled?
		if (configuration.enabled) {
			// Do stuff.
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockRedstoneEventMonitor(final BlockRedstoneEvent event) {
		// Get our block and type.
		final Block block = event.getBlock();
		final int type = block.getTypeId();

		final Configuration.Door configuration = doors.get(type);

		// Do we have configuration data for this?
		if (configuration == null) {
			return;
		}

		// Is this type of door enabled?
		if (configuration.enabled) {
			// Do stuff.
		}
	}
}
