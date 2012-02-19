package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * Set of door pairs.
 *
 * @author moltendorf
 */
abstract class Set {
	// Final data.
	protected final Location location;
	private final World world;

	protected Set(final Location position, final World instance) {
		location = position;
		world = instance;
	}

	protected Set(final Location position) {
		this (position, position.getWorld());
	}

	protected Set(final Location primary, final Location secondary) {
		final World instance = primary.getWorld();
		final double x = (primary.getX() + secondary.getX()) / 2;
		final double z = (primary.getZ() + secondary.getZ()) / 2;

		final Location position = new Location(instance, x, primary.getY(), z);

		location = position;
		world = instance;
	}

	protected boolean equals(final Set_Door_Double set, final Door door, final List list, final long current) {
		return false;
	}

	protected boolean equals(final Set_Door_Single set, final Door door, final List list, final long current) {
		return false;
	}

	protected boolean equals(final Set_FenceGate set) {
		return false;
	}

	protected boolean equals(final Set_Trap set) {
		return false;
	}

	protected void sound() {
		world.playEffect(location, Effect.DOOR_TOGGLE, 0);
	}

	protected boolean apply(final boolean side, final boolean open, final Material material) {
		return false;
	}

	abstract protected boolean apply(final boolean open, final Material material);
	abstract protected boolean powered();
	abstract protected int getPower(final boolean open);
}
