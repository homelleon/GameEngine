package object.gui.pattern.menu;

import object.gui.pattern.button.IGUIButton;
import object.gui.pattern.object.GUIObject;

/**
 * Controls and stores groups of GUI objects.
 * 
 * @author homelleon
 * @see GUIMenu
 */
public interface IGUIMenu {

	/**
	 * Adds {@link GUIObject} into GUI menu.
	 * 
	 * @param object {@link GUIObject} value
	 */
	void add(GUIObject object);
	
	/**
	 * Adds {@link IGUIButton} into GUI menu.
	 * 
	 * @param button {@link IGUIButton} object
	 */
	void addButton(IGUIButton button);
	
	/**
	 * Gets {@link GUIObject} from GUI menu by name.
	 * 
	 * @param name {@link String} value of object name
	 * @return {@link GUIObject} value
	 */
	GUIObject get(String name);
	
	/**
	 * Selects next button.
	 */
	public void selectNextButton();
	
	/**
	 * Selects previous button.
	 */
	public void selectPreviousButton();
	
	/**
	 * Uses selected button.
	 */
	public void useButton();
	
	/**
	 * Gets if the menu has any buttons.
	 * 
	 * @return true if menu has any buttons<br>
	 * 		   false if menu has no buttons 
	 */
	public boolean getHasButtons();
	
	/**
	 * Cleans all objects from menu.
	 */
	void clean();
}
