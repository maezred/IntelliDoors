package com.moltendorf.bukkit.intellidoors;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * IntelliDoors plugin for Bukkit.
 *
 * @author moltendorf
 */
public class Plugin extends JavaPlugin {
	// Variable context.
	protected static Plugin instance = null;
	protected static BukkitScheduler scheduler = null;

	// Final data.
	private final Listener_Interact interactListener = new Listener_Interact();
	private final Listener_Interact_Monitor interactMonitor = new Listener_Interact_Monitor();
	private final Listener_Physics physicsListener = new Listener_Physics();
	private final Listener_Redstone redstoneListener = new Listener_Redstone();

	@Override
	public synchronized void onDisable() {
		final PluginDescriptionFile info = getDescription();

		// Destruct our door object.
		Door.Destruct();

		// Reset our variables.
		instance = null;
		scheduler = null;

		// Stopped.
		System.out.println(info.getName() + " version " + info.getVersion() + " was disabled.");
	}

	@Override
	public synchronized void onEnable() {
		final PluginDescriptionFile info = getDescription();
		final PluginManager manager = getServer().getPluginManager();

		// Create our variables.
		instance = this;
		scheduler = getServer().getScheduler();

		// Register block listeners.
		manager.registerEvent(Type.BLOCK_PHYSICS, physicsListener, Priority.Highest, this);
		manager.registerEvent(Type.REDSTONE_CHANGE, redstoneListener, Priority.Highest, this);

		// Register player listeners.
		manager.registerEvent(Type.PLAYER_INTERACT, interactListener, Priority.Lowest, this);
		manager.registerEvent(Type.PLAYER_INTERACT, interactMonitor, Priority.Monitor, this);

		// Started.
		System.out.println(info.getName() + " version " + info.getVersion() + " was initialized.");
	}
}
