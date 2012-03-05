package com.moltendorf.bukkit.intellidoors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Configuration class.
 *
 * @author moltendorf
 */
class Configuration {
	static protected class Door {
		static protected class Automation {
			static protected enum Mode {
				ANY, // Reset door after either the delay or if the player moves away from the door.
				BOTH, // Reset the door after both the delay and the player moves away from the door.
				DISTANCE, // Reset the door only if the player moves away from the door.
				DELAY, // Reset the door only if the delay finishes.
				DISABLE // Do not reset the door.
			}

			static protected enum State {
				DETECT, // Use redstone current to determine whether the doors should be closed or opened.
				PRESERVE, // Restore the original state of the door (users with the permission node `intellidoors.modify` may change the doors state by sneaking then right-clicking it).
				CLOSE, // Always close doors.
				OPEN // Always open doors.
			}

			// Final data.
			final protected boolean enable = true; // Enable automation of doors (default is true).
			final protected boolean synchronize = true; // Makes sets of double doors open and close together (default is true).

			final protected Mode mode = Mode.ANY; // Which conditions must be satisfied to close the door (default is any).
			final protected int delay = 5; // Automated door reset delay in seconds. (default is 5).
			final protected int distance = 5; // Automated door reset distance in blocks (default is 5).

			final protected State state = State.DETECT; // Which state to use (default is detect).
		}

		static protected class Convenience {
			// Final data.
			final protected int inhibitor = 250; // Minimum time between flipping a door by hand in milliseconds; enter 0 to disable (default is 250).

			final protected boolean scanning = false; // Will be opened by scanning (default false).

			final protected boolean interactable = true; // Allow door to be opened or shut by interacting with it (default is true for all except iron doors).
		}

		static protected class Redstone {
			// Final data.
			final protected boolean enable = true; // Enable redstone manipulation of doors (default is true).
			final protected boolean synchronize = true; // Makes sets of double doors open and close together (default is true).

			final protected int close = 2; // Door shutting from redstone current loss delay in seconds; enter 0 to disable (default is 2).
			final protected int open = 0; // Door opening from redstone current input delay in seconds; enter 0 to disable (default is 0).

			final protected int distance = 1; // The maximum distance doors can sense lingering redstone current from (default is one). Warning: higher values will cause more load!

			final protected HashSet<Integer> source = new HashSet<Integer>(Arrays.asList(new Integer[] { // Blocks that are considered source blocks (default is pressure plates). Warning: more blocks will cause more load!

			}));

			final protected HashSet<Integer> trace = new HashSet<Integer>(Arrays.asList(new Integer[] { // Blocks that are considered trace blocks (default is all blocks that players do not collide with).
				0
			}));
		}

		// Final data.
		final protected boolean enabled = true; // Whether or not this door will be controlled by this plugin (default is true).

		final protected Automation automation = new Automation();
		final protected Convenience convenience = new Convenience();
		final protected Redstone redstone = new Redstone();
	}

	static protected class Global {
		// Final data.
		final protected boolean enabled = true; // Whether or not the plugin is enabled at all; useful for using it as an interface (default is true).

		final protected int scanning = 0; // Maximum distance to scan ahead while sprinting to automatically open doors; enter 0 to disable (default is 0). Warning: experimental, may cause extremely high load! Warning: higher values will cause more load!
	}

	// Final data.
	final protected HashMap<Integer, Door> doors = new HashMap<Integer, Door>();
	final protected Global global = new Global();
}
