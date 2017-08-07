package object.gui.pattern.menu.system;

import java.util.Collection;
import java.util.List;

import object.gui.pattern.menu.IGUIMenu;

public interface IGUIMenuSystem {
	
	/**
	 * Adds GUI menu object into the manager menu array.
	 * 
	 * @param menu {@link IGUIMenu} object
	 */
	void add(IGUIMenu menu);
	
	/**
	 * Adds list of GUI menu array into manager menu array.
	 * @param menuList {@link List}<{@link IGUIMenu}> array of GUI menu
	 */
	void addAll(List<IGUIMenu> menuList);
	
	/**
	 * Gets GUI menu object from manager menu array by its name.
	 * 
	 * @param name {@link String} value of menu name
	 * @return {@link IGUIMenu} object
	 */
	IGUIMenu get(String name);
	
	/**
	 * Gets collection of GUI menu objects form manager menu array.
	 * @return {@link Collection}<{@link IGUIMenu}> array of GUI menu
	 * 		   objects
	 */
	Collection<IGUIMenu> getAll();
	
	/**
	 * Actives GUI menu by name.
	 * 
	 * @param name {@link String} value of menu name
	 * @return {@link IGUIMenu} objec
	 */
	IGUIMenu active(String name);
	
	/**
	 * Deletes GUI menu object by name.
	 * 
	 * @param name {@link String} value of menu name
	 * @return true if object was deleted<br>
	 * 		   false if object wasn't found
	 */
	boolean delete(String name);
	
	/**
	 * Clears all menus from menu array.
	 */
	void clean();

}
