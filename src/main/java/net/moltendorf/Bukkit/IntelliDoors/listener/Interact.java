package net.moltendorf.Bukkit.IntelliDoors.listener;

import net.moltendorf.Bukkit.IntelliDoors.Settings;
import net.moltendorf.Bukkit.IntelliDoors.controller.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static net.moltendorf.Bukkit.IntelliDoors.Settings.Type.DOOR;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public class Interact implements Listener {
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void PlayerInteractEventHandler(PlayerInteractEvent event) {
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Block block = event.getClickedBlock();
      Material material = block.getType();

      Settings.TypeSettings settings = Settings.Companion.getInstance().get(material);

      if (settings != null) {
        Door door = null;

        Settings.Type type = settings.getType();

        if (type == DOOR) {
          if (settings.getPairInteract() && settings.getPairInteractSync()) {
            SingleDoor singleDoor = SingleDoor.Companion.getDoor(block);

            DoubleDoor doubleDoor = DoubleDoor.getDoor(singleDoor);

            if (doubleDoor != null) {
              doubleDoor.wasToggled(singleDoor);

              return;
            }

            door = singleDoor;
          }
        }

        if (door == null) {
          switch (type) {
            case DOOR:
              door = SingleDoor.Companion.getDoor(block);
              break;

            case TRAP:
              door = TrapDoor.getDoor(block);
              break;

            case GATE:
              door = Gate.getDoor(block);
          }

          if (door == null) {
            return;
          }
        }

        if (settings.getSingleInteract()) {
          door.wasToggled(door);
        } else {
          switch (material) {
            case IRON_DOOR_BLOCK:
            case IRON_TRAPDOOR:
              // Do nothing as it won't open anyway.
              break;

            default:
              // Prevent door from opening.
              event.setCancelled(true);
          }
        }
      }
    }
  }
}
