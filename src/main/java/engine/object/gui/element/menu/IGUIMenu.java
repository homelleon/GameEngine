package object.gui.element.menu;

import java.util.List;

import object.Nameable;
import object.gui.Hideable;
import object.gui.element.button.IAction;
import object.gui.element.button.IGUIButton;
import object.gui.element.object.GUIObject;

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
	 * Gets all buttons in List.
	 * 
	 * @return {@link List}<{@link IGUIButton}> List of buttons
	 */
	List<IGUIButton> getAllButtons();
	
	/**
	 * Gets selected button.<br>
	 * Returns null if there is no button is selected.
	 * 
	 * @return {@link IGUIButton} object
	 */
	IGUIButton getSelectedButton();
	
	/**
	 * Selects next button.
	 */
	public void selectNextButton();
	
	/**
	 * Selects previous button.
	 */
	public void selectPreviousButton();
	
	/**
	 * Uses selected button with command directions as param.
	 */
	public void useButton(IAction action);
	
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
