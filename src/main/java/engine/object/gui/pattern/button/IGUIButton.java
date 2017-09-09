package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

import object.Moveable;
import object.Nameable;
import object.bounding.BoundingQuad;
import object.gui.group.IGUIGroup;

/**
 * Represent Interface of graphic user interface button unit.
 * 
 * @author homelleon
 * @see GUIButton
 *
 */
public interface IGUIButton extends Nameable, Moveable<Vector2f> {

	/**
	 * Does selection for current button.<br>
	 * Uses selection action if is initilized. 
	 * 
	 * @return {link IGUIButton} object
	 */
	Thread select();
	
	/**
	 * Does selecetion for current button with action as parameter.
	 * 
	 * @param action {@link IAction} command directions
	 * @return {link IGUIButton} object
	 */
	Thread select(IAction action);

	/**
	 * Does desecelection for current button.<br>
	 * Uses deselection action if is initialized. 
	 *  
	 * @return {link IGUIButton} object
	 */
	Thread deselect();
	
	/**
	 * Does deselecetion for current button with action as parameter.
	 * 
	 * @param action {@link IAction} command directions
	 * @return {link IGUIButton} object
	 */
	Thread deselect(IAction action);
	
	/**
	 * Sets action for button on use.
	 * 
	 * @param action {@link IAction} command directions
	 */
	public void setUseAction(IAction action);
	
	/**
	 * Sets action for button on selectetion.
	 * 
	 * @param action {@link IAction} command directions
	 */
	public void setSelectedAction(IAction action);
	
	/**
	 * Sets action for button on deselection.
	 * 
	 * @param action {@link IAction} command directions
	 */
	public void setDeselectedAction(IAction action);

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
	 * Gets GUI group attached to that button.
	 * 
	 * @return {@link GUIGroup} object
	 */
	IGUIGroup getGroup();
	
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
