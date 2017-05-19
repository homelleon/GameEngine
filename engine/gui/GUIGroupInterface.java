package gui;

import java.util.Collection;

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
	 * Make user group empty destroing all user interfaces.
	 */
	void cleanAll();
}
