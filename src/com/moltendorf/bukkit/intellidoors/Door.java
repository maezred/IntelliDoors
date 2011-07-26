package com.moltendorf.bukkit.intellidoors;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Door wrapper class.
 *
 * @author moltendorf
 */
abstract class Door extends DoorController implements Runnable {
	protected Door(final Set instance, final boolean state, final long current) {
		open = state;
		set = instance;
		time = current;
	}

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
	protected long time = 0;
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

	protected void set(final boolean state) {
		busy = true;
		power = false;

		open = state;
		apply(open);

		busy = false;
	}

	protected void reset() {
		if (set.powered()) {
			busy = true;
			power = true;

			open = true;

			if (apply(open)) {
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

		if (ticks >= 0) {
			schedule(ticks, current);
		}
	}

	private void update(final long ticks, final long current) {
		final long difference = ticks - (current-time)/(1000/rate);

		reset(difference, current);
	}

	private void update(final long current, final Door[] list) {
		final long ticks = power ? reset : delay;

		for (int i = 0; i < list.length; ++i) {
			list[i].update(ticks, current);
		}
	}

	protected boolean equals(final Set_Door_Double instance, final List list, final long current) {
		return set.equals(instance, this, list, current);
	}

	protected boolean equals(final Set_Door_Single instance, final List list, final long current) {
		return set.equals(instance, this, list, current);
	}

	protected boolean equals(final Set_Trap instance) {
		return set.equals(instance);
	}

	protected void merge(final Set instance, final List list, final long current) {
		Door[] doors = {make(instance)};

		cancel();
		update(current, doors);

		list.splice(this, doors);
	}

	protected void split(final Set set, final Pair pair, final List list, final long current) {
		Door[] doors = {make(set), make(new Set_Door_Single(pair))};

		cancel();
		update(current, doors);

		list.splice(this, doors);
	}

	protected synchronized void run(final List list) {
		if (task != -1) {
			task = -1;
			busy = true;

			open = set.powered();

			if (apply(open)) {
				set.sound();
			}

			list.splice(this);
		}
	}

	abstract protected boolean apply(final boolean open);
	abstract protected Door make(final Set set);
}
