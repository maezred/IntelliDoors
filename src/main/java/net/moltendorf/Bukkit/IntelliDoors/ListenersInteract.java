package net.moltendorf.Bukkit.IntelliDoors;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class ListenersInteract implements Listener {
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void PlayerInteractEventHandler(final PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			final Block block = event.getClickedBlock();
			final Material type = block.getType();

			final Settings.TypeSettings settings = Settings.getInstance().getSettings(type);

			if (settings != null) {
				DoorType doorType = null;

				switch (settings.type) {
					case DOOR:
						if (settings.pairInteractEnabled && settings.pairInteractSync) {
							Door door = new Door(block);

							doorType = DoorPair.Get(door);

							if (doorType instanceof DoorPair) {
								// Fun times.
								final boolean closed = doorType.isClosed();

								if (type != Material.IRON_DOOR_BLOCK) {
									// Invert door open state.
									door.bottomData += door.isClosed() ? 4 : -4;
								} else {
									final Location location = block.getLocation();
									if (closed) {
										location.getWorld().playSound(location, Sound.DOOR_OPEN, 1, 1);
									} else {
										location.getWorld().playSound(location, Sound.DOOR_CLOSE, 1, 1);
									}
								}

								if (closed) {
									doorType.open();
								} else {
									doorType.close();
								}
								break;
							}
						} else {
							doorType = new Door(block);
						}

					case TRAP:
					case GATE:
						if (doorType == null) {
							break;
						}

						if (settings.individualInteractEnabled) {
							switch (type) {
								case IRON_DOOR_BLOCK:
								case IRON_TRAPDOOR:
									final Location location = block.getLocation();
									if (doorType.isClosed()) {
										location.getWorld().playSound(location, Sound.DOOR_OPEN, 1, 1);
										doorType.open();
									} else {
										location.getWorld().playSound(location, Sound.DOOR_CLOSE, 1, 1);
										doorType.close();
									}
									break;
							}

							// Continue processing.
						} else {
							switch (type) {
								case IRON_DOOR_BLOCK:
								case IRON_TRAPDOOR:
									// Do nothing as it won't open anyway.
									break;

								default:
									// Prevent door from opening.
									event.setCancelled(true);
							}
						}
						break;
				}
			}
		}
	}
}
