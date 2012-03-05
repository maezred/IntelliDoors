package com.moltendorf.bukkit.intellidoors;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
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

		// Get logger.
		final Logger logger = getLogger();

		// Stopped.
		logger.log(Level.INFO, "{0} version {1} disabled.", new Object[] {name, version});
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

		// Get server.
		final Server server = getServer();

		// Get plugin manager.
		final PluginManager manager = server.getPluginManager();

		// Register our event listeners.
		manager.registerEvents(new Listeners(), this);

		// Get logger.
		final Logger logger = getLogger();

		// Started.
		logger.log(Level.INFO, "{0} version {1} enabled.", new Object[] {name, version});
	}
}
