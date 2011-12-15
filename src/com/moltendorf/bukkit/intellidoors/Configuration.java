package com.moltendorf.bukkit.intellidoors;

import java.util.Arrays;
import java.util.HashSet;
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

	// Final data.
	final protected int ticks = 20; // Number of ticks per second.

	final protected int close = 100; // Automated door closing delay (in ticks).
	final protected int delay = 40; // Door shutting from redstone delay (in ticks).

	final protected int timer = 250; // Minimum time between flipping doors.

	final protected boolean enabled = true; // Whether or not the plugin is enabled at all.
	final protected boolean	redstone = true; // Manipulate redstone current response.

	final protected HashSet<Integer> ignored = new HashSet<Integer>(Arrays.asList(new Integer[] {0})); // Blocks to ignore in door orientation checking.
	final protected HashSet<Integer> interactable = new HashSet<Integer>(Arrays.asList(new Integer[] {})); // Doors that can be opened by interacting.
	final protected HashSet<Integer> standard = new HashSet<Integer>(Arrays.asList(new Integer[] {})); // Doors that will ignore the redstone flag when alone.
	final protected HashSet<Integer> controllable = new HashSet<Integer>(Arrays.asList(new Integer[] {})); // Doors that can be controlled by redstone.
	final protected HashSet<Integer> preserved = new HashSet<Integer>(Arrays.asList(new Integer[] {})); // Doors that have their state preserved (their state can be changed by shift+right-clicking).
}
