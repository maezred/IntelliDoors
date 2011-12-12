package com.moltendorf.bukkit.intellidoors;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Configuration class.
 *
 * @author moltendorf
 */
class Configuration {
	protected Configuration() {
		// Fetch plugin instance and configuration.
		final Plugin instance = Plugin.instance;
		final FileConfiguration configuration = instance.getConfig();
	}
}
