package manager.gui;

import manager.gui.component.IGUIComponentManager;
import manager.gui.group.IGUIGroupManager;
import object.gui.system.IGUIMenuSystem;

/**
 * UI Manager Interface.
 * 
 * @author homelleon
 * @see GUIManager
 */
public interface IGUIManager {

	/**
	 * Initialize ui manager.
	 */
	void initialize();
	
	/**
	 * Gets GUI group manager.
	 * 
	 * @return {@link IGUIGroupManager} object
	 */
	IGUIGroupManager getGroups();
	
	/**
	 * Gets GUI menu system interface.
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
	 * Clean all ui groups.
	 */
	void cleanAll();

}
