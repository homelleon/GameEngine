package object.gui.control.manager;

import java.util.Collection;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import object.gui.control.button.GUIButtonInterface;

/**
 * Manages graphic user interface buttons.
 * 
 * @author homelleon
 *
 */
public interface GUIButtonManagerInterface {
	
	/**
	 * Gets GUI button by name.
	 * 
	 * @param name 
	 * 				- String value of button name
	 * 
	 * @return {@link GUIButtonInterface} value of button 
	 */
	GUIButtonInterface get(String name);
	
	/**
	 * Returns collection of user graphic interface buttons.
	 * 
	 * @return {@link Collection}<{@link GUIButtonInterface}> value of 
	 * 		   buttons array.
	 */
	Collection<GUIButtonInterface> getAll();
	
	/**
	 * Adds graphic user interface button into buttons array.
	 * 
	 * @param button {@GUIButtonInterface} object of button
	 */
	void add(GUIButtonInterface button);
	
	/**
	 * Adds lsit of graphic user interface button into button array.
	 * 
	 * @param buttonList 
	 * 					- {@link List}<{@link GUIButtonInterface}> 
	 * 		  			value of button array.
	 */
	void addAll(List<GUIButtonInterface> buttonList);
	
	/**
	 * Gets list of buttons selected by mouse cursor.
	 * 
	 * @param mouseCoord
	 * 					- {@link Vector2f} value of mouse cursor coordinates
	 * @return {@link List}<{@link GUIButtonInterface}> value of buttons array
	 */
	List<GUIButtonInterface> getMouseOverButton(Vector2f mouseCoord);
	
	/**
	 * Cleans buttons array.
	 */
	void cleanAll();	

}
