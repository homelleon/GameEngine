package object.gui.pattern.manager;

import java.util.Collection;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import object.gui.pattern.button.IGUIButton;

/**
 * Manages graphic user interface buttons.
 * 
 * @author homelleon
 *
 */
public interface IGUIButtonManager {

	/**
	 * Gets GUI button by name.
	 * 
	 * @param name
	 *            - String value of button name
	 * 
	 * @return {@link IGUIButton} value of button
	 */
	IGUIButton get(String name);

	/**
	 * Returns collection of user graphic interface buttons.
	 * 
	 * @return {@link Collection}<{@link IGUIButton}> value of buttons
	 *         array.
	 */
	Collection<IGUIButton> getAll();

	/**
	 * Adds graphic user interface button into buttons array.
	 * 
	 * @param button
	 *            {@GUIButtonInterface} object of button
	 */
	void add(IGUIButton button);

	/**
	 * Adds lsit of graphic user interface button into button array.
	 * 
	 * @param buttonList
	 *            - {@link List}<{@link IGUIButton}> value of button
	 *            array.
	 */
	void addAll(List<IGUIButton> buttonList);

	/**
	 * Gets list of buttons selected by mouse cursor.
	 * 
	 * @param mouseCoord
	 *            - {@link Vector2f} value of mouse cursor coordinates
	 * @return {@link List}<{@link IGUIButton}> value of buttons array
	 */
	List<IGUIButton> getMouseOverButton(Vector2f mouseCoord);

	/**
	 * Cleans buttons array.
	 */
	void clean();

}
