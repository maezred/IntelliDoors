package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.PluginManager;

abstract class Listeners {
	protected static void Enable(final PluginManager manager) {
		// Prepare controllers.
		final Controller controller = new Controller();

		final BlockListenerForwarder blockController = new BlockListenerForwarder(controller);
		final PlayerListenerForwarder playerController = new PlayerListenerForwarder(controller);

		// Prepare monitors.
		final Monitor monitor = new Monitor();

		final PlayerListenerForwarder playerMonitor = new PlayerListenerForwarder(monitor);

		// Fetch plugin instance.
		final Plugin instance = Plugin.instance;

		// Register block listeners.
		manager.registerEvent(Type.BLOCK_PHYSICS, blockController, Priority.Highest, instance);
		manager.registerEvent(Type.REDSTONE_CHANGE, blockController, Priority.Highest, instance);

		// Register player listeners.
		manager.registerEvent(Type.PLAYER_INTERACT, playerController, Priority.Lowest, instance);
		manager.registerEvent(Type.PLAYER_INTERACT, playerMonitor, Priority.Monitor, instance);
	}
}

/**
 * Listener callback interface.
 *
 * @author moltendorf
 */
abstract class Listener {
	public void onBlockPhysics(final BlockPhysicsEvent event) {}
	public void onBlockRedstoneChange(final BlockRedstoneEvent event) {}
	public void onPlayerInteract(final PlayerInteractEvent event) {}
}

class Controller extends Listener{
	@Override public void onBlockPhysics(final BlockPhysicsEvent event) {

	}

	@Override public void onBlockRedstoneChange(final BlockRedstoneEvent event) {

	}

	@Override public void onPlayerInteract(final PlayerInteractEvent event) {

	}
}

class Monitor extends Listener {
	@Override public void onPlayerInteract(final PlayerInteractEvent event) {

	}
}

class PlayerListenerForwarder extends PlayerListener {
	PlayerListenerForwarder(final Listener instance) {
		listener = instance;
	}

	final Listener listener;

	@Override public void onPlayerInteract(final PlayerInteractEvent event) {
		listener.onPlayerInteract(event);
	}
}

class BlockListenerForwarder extends BlockListener {
	BlockListenerForwarder(final Listener instance) {
		listener = instance;
	}

	final Listener listener;

	@Override public void onBlockPhysics(final BlockPhysicsEvent event) {
		listener.onBlockPhysics(event);
	}

	@Override public void onBlockRedstoneChange(final BlockRedstoneEvent event) {
		listener.onBlockRedstoneChange(event);
	}
}
