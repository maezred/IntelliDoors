package net.moltendorf.Bukkit.IntelliDoors.listener;

import net.moltendorf.Bukkit.IntelliDoors.controller.SingleDoor;
import net.moltendorf.Bukkit.IntelliDoors.controller.DoubleDoor;
import net.moltendorf.Bukkit.IntelliDoors.controller.Door;
import net.moltendorf.Bukkit.IntelliDoors.Settings;
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
public class Interact implements Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void PlayerInteractEventHandler(final PlayerInteractEvent event) {
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      final Block block = event.getClickedBlock();
      final Material type = block.getType();

      final Settings.TypeSettings settings = Settings.getInstance().getSettings(type);

      if (settings != null) {
        Door door = null;

        switch (settings.getType()) {
          case DOOR:
            if (settings.isPairInteractEnabled() && settings.isPairInteractSync()) {
              SingleDoor singleDoor = new SingleDoor(block);

              door = DoubleDoor.Get(singleDoor);

              if (door instanceof DoubleDoor) {
                door.wasToggled(singleDoor);
                break;
              }
            } else {
              door = new SingleDoor(block);
            }

          case TRAP:
          case GATE:
            if (door == null) {
              break;
            }

            if (settings.isIndividualInteractEnabled()) {
              switch (type) {
                case IRON_DOOR_BLOCK:
                case IRON_TRAPDOOR:
                  final Location location = block.getLocation();
                  if (door.isClosed()) {
                    location.getWorld().playSound(location, Sound.BLOCK_IRON_TRAPDOOR_OPEN, 1, 1);
                    door.open();
                  } else {
                    location.getWorld().playSound(location, Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 1, 1);
                    door.close();
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
