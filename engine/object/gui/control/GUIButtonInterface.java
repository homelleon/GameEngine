package object.gui.control;

import org.lwjgl.util.vector.Vector2f;

/**
 * Represent Interface of graphic user interface button unit.
 * 
 * @author homelleon
 *
 */
public interface GUIButtonInterface {
	
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
	 * Sets to show current button for rendering engine.
	 */
	void show();
	
	/**
	 * Sets to hide current button for rendering engine.
	 */
	void hide();
	
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

}
