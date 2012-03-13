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
public class Listeners implements Listener {
	final protected HashMap<Integer, Configuration.Door> doors = Plugin.configuration.doors;

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void PlayerInteractEventHandler(final PlayerInteractEvent event) {
		// Are we enabled at all?
		if (!Plugin.configuration.global.enabled) {
			return;
		}

		// Do we have a block?
		if (!event.hasBlock()) {
			return;
		}

		// Get our block and type.
		final Block block = event.getClickedBlock();
		final int type = block.getTypeId();

		final Configuration.Door configuration = doors.get(type);

		// Do we have configuration data for this (this should never map to null, so if it's null legitimately, we can't really use it anyway)?
		if (configuration == null) {
			return;
		}

		// Is this type of door enabled?
		if (!configuration.enabled) {
			return;
		}

		Door.Debug(block);

		// Is interacting with this type of door enabled?
		if (!configuration.convenience.interactable) {
			event.setCancelled(true); // Cancel the event.

			return;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void PlayerInteractEventMonitor(final PlayerInteractEvent event) {
		// Are we enabled at all?
		if (!Plugin.configuration.global.enabled) {
			return;
		}

		// Do we have a block?
		if (!event.hasBlock()) {
			return;
		}

		// Get our block and type.
		final Block block = event.getClickedBlock();
		final int type = block.getTypeId();

		final Configuration.Door configuration = doors.get(type);

		// Do we have configuration data for this (this should never map to null, so if it's null legitimately, we can't really use it anyway)?
		if (configuration == null) {
			return;
		}

		// Is this type of door enabled?
		if (!configuration.enabled) {
			return;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockRedstoneEventMonitor(final BlockRedstoneEvent event) {
		// Are we enabled at all?
		if (!Plugin.configuration.global.enabled) {
			return;
		}

		// Get our block and type.
		final Block block = event.getBlock();
		final int type = block.getTypeId();

		final Configuration.Door configuration = doors.get(type);

		// Do we have configuration data for this (this should never map to null, so if it's null legitimately, we can't really use it anyway)?
		if (configuration == null) {
			return;
		}

		// Is this type of door enabled?
		if (!configuration.enabled) {
			return;
		}
	}
}
