package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Main plugin class for IntelliDoors.
 *
 * @author moltendorf
 */
public class Plugin extends JavaPlugin {
	// Variable context.
	protected static Plugin instance = null;
	protected static String name = null;
	protected static String version = null;
	protected static BukkitScheduler scheduler = null;

	@Override
	public synchronized void onDisable() {
		// Reset our variables.
		instance = null;
		name = null;
		version = null;
		scheduler = null;

		// Stopped.
		System.out.println(name+" version "+version+" was disabled.");
	}

	@Override
	public synchronized void onEnable() {
		if (instance != null) {
			instance.onDisable();
		}

		final PluginDescriptionFile description = getDescription();
		final Server server = getServer();
		final PluginManager manager = server.getPluginManager();

		// Create our variables.
		instance = this;
		name = description.getName();
		version = description.getVersion();
		scheduler = server.getScheduler();

		// Register events.
		Listeners.Enable(manager);

		// Started.
		System.out.println(name+" version "+version+" was initialized.");
	}
}
