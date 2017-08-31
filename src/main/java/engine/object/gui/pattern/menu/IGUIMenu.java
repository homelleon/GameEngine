package object.gui.pattern.menu;

import object.Nameable;
import object.gui.Hideable;
import object.gui.pattern.button.IEvent;
import object.gui.pattern.object.GUIObject;

/**
 * Controls and stores groups of GUI objects.
 * 
 * @author homelleon
 * @see GUIMenu
 */
public interface IGUIMenu extends Hideable,Nameable {

	/**
	 * Adds {@link GUIObject} into GUI menu.
	 * 
	 * @param object {@link GUIObject} value
	 */
	void add(GUIObject object);
	
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
	public void useButton(IEvent event);
	
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
