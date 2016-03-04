package net.moltendorf.Bukkit.IntelliDoors;

/**
 * Created by moltendorf on 15/05/23.
 *
 * @author moltendorf
 */
abstract public class DoorType {
	abstract public void open();
	abstract public void close();

	abstract public boolean isOpened();
	abstract public boolean isClosed();
}
