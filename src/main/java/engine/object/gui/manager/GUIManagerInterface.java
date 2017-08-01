package object.gui.manager;

import object.gui.component.GUIComponentManagerInterface;
import object.gui.group.manager.GUIGroupManagerInterface;
import object.gui.pattern.menu.system.GUIMenuSystemInterface;

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
	void initialize();
	
	/**
	 * Gets GUI group manager.
	 * 
	 * @return {@link GUIGroupManagerInterface} object
	 */
	GUIGroupManagerInterface getGroups();
	
	/**
	 * Gets GUI menu system interface.
	 * 
	 * @return {@link GUIMenuSystemInterface} tool
	 */
	GUIMenuSystemInterface getMenus();

	/**
	 * Returns component manager that controls engine default interface
	 * entities.
	 * 
	 * @return {@link GUIComponentManagerInterface} value
	 */
	GUIComponentManagerInterface getComponent();

	/**
	 * Renders graphic user interface.
	 */
	void render();

	/**
	 * Clean all ui groups.
	 */
	void cleanAll();

}
