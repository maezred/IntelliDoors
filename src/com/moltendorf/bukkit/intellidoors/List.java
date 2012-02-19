package com.moltendorf.bukkit.intellidoors;

/**
 * Door list.
 *
 * @author moltendorf
 */
class List {
	// Variable data.
	private Door first = null, last = null, pointer = null;

	protected synchronized void push(final Door door) {
		if (first == null) {
			first = door;
		} else {
			door.previous = last;
			last.next = door;
		}

		last = door;
	}

	protected synchronized void splice(final Door door) {
		if (door.previous == null) {
			first = door.next;
		} else {
			door.previous.next = door.next;
		}

		if (door.next == null) {
			last = door.previous;
		} else {
			door.next.previous = door.previous;
		}

		door.next = null;
		door.previous = null;
	}

	protected synchronized void splice(final Door door, final Door[] list) {
		if (door.previous == null) {
			first = list[0];
		} else {
			list[0].previous = door.previous;
			door.previous.next = list[0];
		}

		if (door.next == null) {
			last = list[list.length-1];
		} else {
			list[list.length-1].next = door.next;
			door.next.previous = list[list.length-1];
		}

		door.next = null;
		door.previous = null;

		pointer = list[0];

		for (int i = 1; i < list.length; ++i) {
			list[i-1].next = list[i];
			list[i].previous = list[i-1];
		}
	}

	protected synchronized void destruct() {
		for (Door current = first; current != null; current = pointer) {
			pointer = current.next;

			current.cancel();
			current.run(this);
		}

		pointer = null;
	}

	private Door scan(final Set_Door_Double set, final long time) {
		for (Door current = first; current != null; current = pointer) {
			pointer = current.next;

			if (current.equals(set, this, time)) {
				return current;
			}
		}

		return null;
	}

	protected synchronized Door get(final Set_Door_Double set, final long current) {
		final Door door = scan(set, current);

		pointer = null;

		return door;
	}

	private Door scan(final Set_Door_Single set, final long time) {
		for (Door current = first; current != null; current = pointer) {
			pointer = current.next;

			if (current.equals(set, this, time)) {
				return current;
			}
		}

		return null;
	}

	protected synchronized Door get(final Set_Door_Single set, final long current) {
		final Door door = scan(set, current);

		pointer = null;

		return door;
	}

	protected synchronized Door get(final Set_FenceGate set) {
		for (Door current = first; current != null; current = current.next) {
			if (current.equals(set)) {
				return current;
			}
		}

		return null;
	}

	protected synchronized Door get(final Set_Trap set) {
		for (Door current = first; current != null; current = current.next) {
			if (current.equals(set)) {
				return current;
			}
		}

		return null;
	}
}
