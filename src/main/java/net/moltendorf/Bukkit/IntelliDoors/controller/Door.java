package net.moltendorf.Bukkit.IntelliDoors.controller;

import org.bukkit.Material;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public interface Door {
  void open();

  void close();

  void toggle();

  void overrideState(boolean closed);

  boolean isOpened();

  boolean isClosed();

  Material getType();

  void wasToggled(Door onDoor);
}
