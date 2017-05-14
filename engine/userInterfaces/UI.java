package userInterfaces;

import org.lwjgl.util.vector.Vector2f;

/**
 * User interafce to represent connection between enigine and user.
 *
 * 
 * @author homelleon
 *
 */
public interface UI {
	
	/**
	 * Returns name of user interface object.
	 * 
	 * @return {@link String} value
	 */
	String getName();
	
	/**
	 * Sets user interface object visible.
	 */
	void show();
	
	/**
	 * Sets user interface object invisible.
	 */
	void hide();
	
	/**
	 * 
	 * Sets transparency value for user interface object.
	 *
	 * @param value {@link Float}
	 */
	void setTransparency(float value);
	
	/**
	 * Moves user interface object at schedule position depending on its
	 * starting position.
	 * 
	 * @param position {@link Vector2f} value
	 */
	void move(Vector2f position);
	
	/**
	 * Clear user interface and delete it.
	 */
	void delete();
	
}
