package object.gui.system;

import object.gui.Hideable;
import object.gui.element.menu.IGUIMenu;
import tool.manager.IManager;

/**
 * GUI menu system control interface.
 *  
 * @author homelleon
 * @see GUIMenuSystem
 */
public interface IGUIMenuSystem extends IManager<IGUIMenu>, Hideable {
	
	/**
	 * Actives GUI menu by name.
	 * 
	 * @param name {@link String} value of menu name
	 * @return {@link IGUIMenu} object
	 */
	IGUIMenu active(String name);
	
	/**
	 * Gets activated menu 
	 * @return {@link IGUIMenu} object
	 */
	IGUIMenu getActivated();
	
	/**
	 * Shows menu with defined name.
	 * 
	 * @param name String value of menu name
	 */
	void show(String name);
	
	/**
	 * Shows menu with defined name.
	 * 
	 * @param name String value of menu name
	 */
	void hide(String name);

}
