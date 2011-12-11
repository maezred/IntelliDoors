package com.moltendorf.bukkit.intellidoors;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for IntelliDoors.
 *
 * @author moltendorf
 */
public class Plugin extends JavaPlugin {
	// Variable context.
	protected static Plugin instance = null;

	// Variable data.
	protected String name = null;
	protected String version = null;

	@Override
	public synchronized void onDisable() {
		// Clear context.
		instance = null;

		// Clear data.
		name = null;
		version = null;

		// Stopped.
		System.out.println(name+" version "+version+" disabled.");
	}

	@Override
	public synchronized void onEnable() {
		if (instance != null) {
			instance.onDisable();
		}

		// Prepare context.
		instance = this;

		// Get description.
		final PluginDescriptionFile description = getDescription();

		// Prepare data.
		name = description.getName();
		version = description.getVersion();

		// Register events.
		Listeners.Enable();

		// Started.
		System.out.println(name+" version "+version+" enabled.");
	}
}
