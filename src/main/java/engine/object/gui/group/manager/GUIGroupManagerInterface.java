package object.gui.group.manager;

import java.util.Collection;

import object.gui.group.GUIGroupInterface;

/**
 * 
 * @author homelleon
 * @see GUIGroupManager
 */
public interface GUIGroupManagerInterface {

	/**
	 * Creates empty GUI Group and add it to guiManager.
	 * 
	 * @param name
	 *            string value of group name
	 * 
	 * @return {@link GUIGroupInterface} value of group
	 */
	GUIGroupInterface createEmpty(String name);

	/**
	 * Adds list of user interface groups.
	 * 
	 * @param groupList
	 */
	void addAll(Collection<GUIGroupInterface> groupList);

	/**
	 * Adds user interface group.
	 * 
	 * @param groupList
	 */
	void add(GUIGroupInterface group);

	/**
	 * Returns UIGroup by name.
	 * 
	 * @param name
	 *            {@link String} value
	 * 
	 * @return {@link GUIGroupInterface} value of user interfaces
	 */
	GUIGroupInterface get(String name);

	/**
	 * Returns list of all UIGroups.
	 * 
	 * @return {@link Collection}<{@link GUIGroupInterface}> value of all user
	 *         interface groups
	 */
	Collection<GUIGroupInterface> getAll();

	/**
	 * Deletes GUI group and its all content from memory.
	 * 
	 * @param name
	 *            string value of GUI group name
	 * @return true if group is exist<br>
	 *         false if group isn't exist
	 */
	boolean delete(String name);
	
	/**
	 * Clears all groups from groups array.
	 */
	void clean();

}
