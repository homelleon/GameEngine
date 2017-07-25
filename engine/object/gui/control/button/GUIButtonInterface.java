package object.gui.control.button;

import org.lwjgl.util.vector.Vector2f;

import object.gui.control.object.GUIObject;

/**
 * Represent Interface of graphic user interface button unit.
 * 
 * @author homelleon
 *
 */
public interface GUIButtonInterface extends GUIObject {
	
	/**
	 * Does selecetion action for current button.
	 */
	void select();
	
	/**
	 * Does desecelection action for current button.
	 */
	void deselect();
	
	/**
	 * Uses button command that was pre-implemented.
	 */
	void use();
	
	/**
	 * Checks if mouse is pointing current button.
	 * 
	 * @return true if mouse is over the current button<br>
	 * 		   false if button is out of the current button
	 */
	boolean getIsMouseOver(Vector2f cursorPosition);
	
	/**
	 * Checks if the current button is selected or not.
	 * 
	 * @return true if the current button is selected<br>
	 * 		   false if the current button is not selected 
	 */
	boolean getIsSelected();
	
	/**
	 * Gets graphic user interface name.
	 * 
	 * @return {@link String} value of gui button name
	 */
	String getName();

}
