package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

import object.Moveable;
import object.Nameable;
import object.bounding.BoundingQuad;

/**
 * Represent Interface of graphic user interface button unit.
 * 
 * @author homelleon
 * @see GUIButton
 *
 */
public interface IGUIButton extends Nameable, Moveable<Vector2f> {

	/**
	 * Does selecetion action for current button.
	 */
	IGUIButton select();

	/**
	 * Does desecelection action for current button.
	 */
	void deselect();
	
	/**
	 * Attaches action for button command.
	 * 
	 * @param action {@link IAction} command directions
	 */
	public void attachAction(IAction action);

	/**
	 * Uses button command throw param.
	 * @param action {@link IAction} commad directions
	 */
	void use(IAction action);
	
	/**
	 * Uses button command with pre-attached event.<br>
	 * If event wasn't attached this method does nothing.
	 */
	void use();
	
	/**
	 * Sets quad is bounding over the button that can be used for verification
	 * of intersection between the button and the mouse pointer.
	 * 
	 * @param quad {@link BoundingQuad} object
	 * @param centered boolean value indicates if quad have to be recalculated
	 * to match the GUI textures are in the button array.
	 */
	void setBoundingArea(BoundingQuad quad, boolean centered);
	
	/**
	 * Gets bounding area over the button that can be used for verification
	 * of intersection between the button and the mouse pointer.
	 * 
	 * @return {@link BoundingQuad} bounding are
	 */
	BoundingQuad getBoundingArea();

	/**
	 * Checks if mouse is pointing current button.
	 * 
	 * @return true if mouse is over the current button<br>
	 *         false if button is out of the current button
	 */
	boolean getIsMouseOver(Vector2f cursorPosition);
	
	/**
	 * Sets scale for all GUI objects of current button.
	 * 
	 * @param scale {@link Vector2f} value of scale
	 */
	void increaseScale(Vector2f scale);

	/**
	 * Checks if the current button is selected or not.
	 * 
	 * @return true if the current button is selected<br>
	 *         false if the current button is not selected
	 */
	boolean getIsSelected();

}
