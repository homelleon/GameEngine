package object.gui.pattern.menu.system;

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

}
