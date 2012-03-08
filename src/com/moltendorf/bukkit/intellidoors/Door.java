package com.moltendorf.bukkit.intellidoors;

import java.util.logging.Level;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

/**
 * Primary door class.
 *
 * @author moltendorf
 */
public class Door {
	public static void Debug(final Block block) {
		final int data = block.getData();
		final int type = block.getTypeId();

		final int top, bottom;

		if ((data & 8) == 0) {
			top = block.getRelative(BlockFace.UP).getData();
			bottom = data;
		} else {
			top = data;
			bottom = block.getRelative(BlockFace.DOWN).getData();
		}

		Plugin.instance.getLogger().log(Level.INFO, String.format("Door type %1$s has data of %2$s. This is the %3$s of the door, its hinge is on the %4$s, it's facing %5$s and it was %6$s.", new Object[] {type, data, (data & 8) == 0 ? "BOTTOM" : "TOP", (top & 1) == 0 ? "LEFT" : "RIGHT", new String[] {"WEST", "NORTH", "EAST", "SOUTH"} [bottom & 3], (bottom & 4) == 0 ? "CLOSED" : "OPEN"}));
	}
}
