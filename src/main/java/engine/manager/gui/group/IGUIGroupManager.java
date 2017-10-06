package manager.gui.group;

import java.util.Collection;

import object.gui.group.IGUIGroup;

/**
 * 
 * @author homelleon
 * @see GUIGroupManager
 */
public interface IGUIGroupManager {

	/**
	 * Creates empty GUI Group and add it to guiManager.
	 * 
	 * @param name
	 *            string value of group name
	 * 
	 * @return {@link IGUIGroup} value of group
	 */
	IGUIGroup createEmpty(String name);

	/**
	 * Adds collection of user interface groups.
	 * 
	 * @param groupList
	 */
	void addAll(Collection<IGUIGroup> groupList);

	/**
	 * Adds user interface group.
	 * 
	 * @param groupList
	 */
	void add(IGUIGroup group);

	/**
	 * Returns UIGroup by name.
	 * 
	 * @param name
	 *            {@link String} value
	 * 
	 * @return {@link IGUIGroup} value of user interfaces
	 */
	IGUIGroup get(String name);

	/**
	 * Returns list of all UIGroups.
	 * 
	 * @return {@link Collection}<{@link IGUIGroup}> value of all user
	 *         interface groups
	 */
	Collection<IGUIGroup> getAll();

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
