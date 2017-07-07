package object.gui.manager;

import java.util.Collection;

import object.gui.component.GUIComponentManagerInterface;
import object.gui.group.GUIGroupInterface;
import renderer.Loader;

/**
 * UI Manager Interface.
 * 
 * @author homelleon
 * @see GUIManager
 */
public interface GUIManagerInterface {
	
	/**
	 * Initialize ui manager.
	 */
	void init(Loader loader);	
	
	/**
	 * Adds list of user interface groups.
	 * 
	 * @param groupList
	 */
	void addAllGUIGroups(Collection<GUIGroupInterface> groupList);
	
	/**
	 * Adds user interface group.
	 * 
	 * @param groupList
	 */
	void addGUIGroup(GUIGroupInterface group);
	
	/**
	 * Returns UIGroup by name.
	 * 
	 * @param name {@link String} value
	 * 
	 * @return {@link GUIGroupInterface} value of user interfaces
	 */
	GUIGroupInterface getGUIGroup(String name);
	
	/**
	 * Returns list of all UIGroups.
	 * 
	 * @return {@link Collection}<{@link GUIGroupInterface}> value of all user
	 * interface groups
	 */
	Collection<GUIGroupInterface> getAllGUIGroups();
	
	/**
	 * Returns component manager that controls engine default interface
	 * entities.
	 *  
	 * @return {@link GUIComponentManagerInterface} value
	 */
	GUIComponentManagerInterface getComponent();
	
	/**
	 * Clean all ui groups.
	 */
	void cleanAll();

}
