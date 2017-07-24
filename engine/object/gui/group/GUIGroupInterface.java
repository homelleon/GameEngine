package object.gui.group;

import java.util.Collection;
import java.util.List;

import object.gui.gui.GUIInterface;

/**
 * Group of user interface objects to solve the same task.
 * 
 * @author homelleon
 * @see GUIGroup
 *
 */
public interface GUIGroupInterface {
	
	/**
	 * Returns name of the user interface group.
	 * 
	 * @return {@link String} value
	 */
	String getName();
	
	/**
	 * Adds new graphic user interface into guis array.
	 * 
	 * @param gui {@link GUIInterface} value of graphic user interface 
	 */
	void add(GUIInterface gui);
	
	/**
	 * Adds new list of graphic user interface into guis array.
	 *  
	 * @param guiList {@link List}<{@link GUIInterface}> list of graphic user
	 * 		  interfaces
	 */
	void addAll(List<GUIInterface> guiList);
	
	/**
	 * Returns user interface object by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * @return {@link GUIInterface} value of user interface object
	 */
	GUIInterface get(String name);
	
	/**
	 * Set all user interface in the group visible.
	 */
	void showAll();
	
	/**
	 * Set all user interface in the group invisible.
	 */
	void hideAll();
	
	/**
	 * Returns collection of user interface objects.
	 * 
	 * @return {@link Collection}<{@link GUIInterface}> value of user interfaces
	 */
	Collection<GUIInterface> getAll();
	
	
	/**
	 * Returns render priority number of current group.
	 * 
	 * @return int value of priority number
	 */
	int getPriorityNumber();
	
	/**
	 * Sets render priority value of current group.
	 * 
	 * @param number int value of priority number
	 */
	void setPriorityNumber(int number);
	
	/**
	 * Make user group empty destroing all user interfaces.
	 */
	void cleanAll();
}
