package com.moltendorf.bukkit.intellidoors;

/**
 * Handles all persistence data for Doors.
 *
 * @author moltendorf
 */
abstract class DoorController extends BlockInfo {
	// Constants.
	protected static final int minimum = 250; // Minimum time between flipping doors.
	protected static final int rate = 20; // Number of ticks per second.
	protected static final int delay = 100; // Scheduled task delay (in ticks).
	protected static final int reset = 40; // Scheduled task delay (in ticks) for redstone resetting.

	protected static void Destruct() {
		Door_Iron.Destruct();
		Door_Trap.Destruct();
		Door_Wood.Destruct();
	}
}
