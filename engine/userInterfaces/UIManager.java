package userInterfaces;

import java.util.Collection;

/**
 * UI Manager Interface.
 * 
 * @author homelleon
 * @see UIManagerBasic
 */
public interface UIManager {
	
	/**
	 * Initialize ui manager.
	 */
	void init();
	
	/**
	 * Returns UIGroup by name.
	 * 
	 * @param name {@link String} value
	 * 
	 * @return {@link UIGroup} value of user interfaces
	 */
	UIGroup getUIGroup(String name);
	
	/**
	 * Adds list of user interface groups.
	 * 
	 * @param groupList
	 */
	void addUIGroup(Collection<UIGroup> groupList);
	
	/**
	 * Returns component manager that controls engine default interface
	 * entities.
	 *  
	 * @return {@link UIComponentManager} value
	 */
	UIComponentManager getComponentManager();
	
	/**
	 * Clean all ui groups.
	 */
	void cleanAll();

}
