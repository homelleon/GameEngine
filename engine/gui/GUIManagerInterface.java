package gui;

import java.util.Collection;

import renderEngine.Loader;

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
	 * Returns UIGroup by name.
	 * 
	 * @param name {@link String} value
	 * 
	 * @return {@link GUIGroupInterface} value of user interfaces
	 */
	GUIGroupInterface getUIGroup(String name);
	
	/**
	 * Adds list of user interface groups.
	 * 
	 * @param groupList
	 */
	void addUIGroup(Collection<GUIGroupInterface> groupList);
	
	/**
	 * Returns component manager that controls engine default interface
	 * entities.
	 *  
	 * @return {@link GUIComponentManagerInterface} value
	 */
	GUIComponentManagerInterface getComponentManager();
	
	/**
	 * Clean all ui groups.
	 */
	void cleanAll();

}
