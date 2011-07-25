package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Door wrapper class.
 *
 * @author moltendorf
 */
abstract class Door extends DoorController implements Runnable {
	protected Door(final Set instance, final boolean state) {
		open = state;
		set = instance;
	}

	// Final data.
	protected final Set set;

	// Variable data.
	protected boolean busy = false;
	protected boolean open = false;
	protected boolean power = true;
	private int task = -1;
	private long time = 0;
	protected Door next = null, previous = null;

	private static Handler CreateSet(final Material material, final Block block, final Listener listener) {
		final Pair original, primary, secondary;

		final int data = GetData(block);

		if (IsBottom(data)) {
			original = new Pair(block, data);
		} else {
			original = new Pair(GetBottom(block), block, data-8);
		}

		primary = original.reverse() ? original.getPrimary(material) : null;
		secondary = original.forward() ? original.getSecondary(material) : null;

		if (primary != null) {
			if (secondary != null) {
				// We don't let doors be members of multiple sets.
				return listener.make(material, new Set_Door_Single(original), original.open(data));
			}

			if (primary.getPrimary(material) != null) {
				// We don't let doors be members of multiple sets.
				return listener.make(material, new Set_Door_Single(original), original.open(data));
			}

			return listener.make(material, new Set_Door_Double(primary, original), original.open(data), false);
		} else if (secondary != null) {
			if (secondary.getSecondary(material) != null) {
				// We don't let doors be members of multiple sets.
				return listener.make(material, new Set_Door_Single(original), original.open(data));
			}

			return listener.make(material, new Set_Door_Double(original, secondary), original.open(data), true);
		}

		return listener.make(material, new Set_Door_Single(original), original.open(data));
	}

	protected static Handler Get(final Material material, final Block block, final Listener listener) {
		if (material == Material.TRAP_DOOR) {
			final int data = GetData(block);

			return listener.make(new Set_Trap(block, data), IsOpen(data));
		}

		return CreateSet(material, block, listener);
	}

	protected boolean busy() {
		return busy;
	}

	protected boolean busy(final long current) {
		return busy || minimum > current-time;
	}

	protected boolean power() {
		return power;
	}

	protected int getPower() {
		return set.getPower(open);
	}

	protected int getPower(final boolean side) {
		return open == side ? 1 : 0;
	}

	protected void apply(final boolean state) {
		busy = true;
		power = false;

		open = state;
		set.apply(open);

		busy = false;
	}

	protected void reset() {
		if (set.powered()) {
			busy = true;
			power = true;

			open = true;

			if (set.apply(open)) {
				set.sound();
			}

			busy = false;
		}
	}

	private void schedule(final long ticks, final long current) {
		task = Plugin.scheduler.scheduleSyncDelayedTask(Plugin.instance, this, ticks);
		time = current;
	}

	protected void cancel() {
		Plugin.scheduler.cancelTask(task);

		task = -1;
	}

	protected void reset(final long ticks, final long current) {
		if (task != -1) {
			cancel();
		}

		schedule(ticks, current);
	}

	private void update(final long ticks, final long current) {
		final long difference = (current-time)/(1000/rate);

		reset(difference, current);
	}

	protected boolean equals(final Set_Door_Double instance, final List list) {
		return set.equals(instance, this, list);
	}

	protected boolean equals(final Set_Door_Single instance, final List list) {
		return set.equals(instance, this, list);
	}

	protected boolean equals(final Set_Trap instance) {
		return set.equals(instance);
	}

	protected void merge(final Set instance, final List list) {
		list.splice(this, new Door[] {make(instance)});
	}

	protected void split(final Set set, final Pair pair, final List list) {
		list.splice(this, new Door[] {make(set), make(new Set_Door_Single(pair))});
	}

	protected synchronized void run(final List list) {
		busy = true;

		open = set.powered();

		if (set.apply(open)) {
			set.sound();
		}

		list.splice(this);
	}

	abstract protected Door make(final Set set);
}
