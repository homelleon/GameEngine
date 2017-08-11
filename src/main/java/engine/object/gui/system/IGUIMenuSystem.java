package object.gui.system;

import object.gui.Hideable;
import object.gui.pattern.menu.IGUIMenu;
import tool.manager.IManager;

public interface IGUIMenuSystem extends IManager<IGUIMenu>, Hideable {
	
	/**
	 * Actives GUI menu by name.
	 * 
	 * @param name {@link String} value of menu name
	 * @return {@link IGUIMenu} objec
	 */
	IGUIMenu active(String name);
	
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
