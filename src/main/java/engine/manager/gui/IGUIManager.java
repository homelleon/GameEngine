package manager.gui;

import manager.gui.component.IGUIComponentManager;
import manager.gui.group.IGUIGroupManager;
import object.gui.system.IGUIMenuSystem;

/**
 * Porvide access to graphic user interface.
 * 
 * @author homelleon
 * @see GUIManager
 */
public interface IGUIManager {

	/**
	 * Initialize user interface manager.
	 */
	void initialize();
	
	/**
	 * Gets graphic user interface group manager.
	 * 
	 * @return {@link IGUIGroupManager} object
	 */
	IGUIGroupManager getGroups();
	
	/**
	 * Gets graphic user interface menu system.
	 * 
	 * @return {@link IGUIMenuSystem} tool
	 */
	IGUIMenuSystem getMenus();

	/**
	 * Returns component manager that controls engine default interface
	 * entities.
	 * 
	 * @return {@link IGUIComponentManager} value
	 */
	IGUIComponentManager getComponent();

	/**
	 * Renders graphic user interface.
	 */
	void render();

	/**
	 * Clean all graphic user interface groups.
	 */
	void cleanAll();

}
