package net.moltendorf.Bukkit.IntelliDoors.controller;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
public interface Door {
  void open();

  void close();

  void toggle();

  boolean isOpened();

  boolean isClosed();

  void wasToggled(SingleDoor onDoor);
}
