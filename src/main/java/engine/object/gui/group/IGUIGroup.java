package object.gui.group;

import java.util.Collection;
import java.util.List;

import object.Moveable;
import object.gui.Hideable;
import object.gui.gui.IGUI;
import tool.math.vector.Vec2f;

/**
 * Group of user interface objects to solve the same task.
 * 
 * @author homelleon
 * @see GUIGroup
 *
 */
public interface IGUIGroup extends Hideable, Moveable<Vec2f> {

	/**
	 * Returns name of the user interface group.
	 * 
	 * @return {@link String} value
	 */
	String getName();

	/**
	 * Adds new graphic user interface into guis array.
	 * 
	 * @param gui
	 *            {@link IGUI} value of graphic user interface
	 */
	void add(IGUI gui);

	/**
	 * Adds new list of graphic user interface into guis array.
	 * 
	 * @param guiList
	 *            {@link List}<{@link IGUI}> list of graphic user
	 *            interfaces
	 */
	void addAll(List<IGUI> guiList);

	/**
	 * Returns user interface object by name.
	 * 
	 * @param name
	 *            {@link String} value
	 * @return {@link IGUI} value of user interface object
	 */
	IGUI get(String name);

	/**
	 * Returns collection of user interface objects.
	 * 
	 * @return {@link Collection}<{@link IGUI}> value of user interfaces
	 */
	Collection<IGUI> getAll();

	/**
	 * Returns render priority number of current group.
	 * 
	 * @return int value of priority number
	 */
	int getPriorityNumber();

	/**
	 * Sets render priority value of current group.
	 * 
	 * @param number
	 *            int value of priority number
	 */
	void setPriorityNumber(int number);

	/**
	 * Make user group empty destroing all user interfaces.
	 */
	void clean();
}
