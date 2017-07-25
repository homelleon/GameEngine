package object.gui.gui;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import object.gui.Hideable;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

/**
 * User interafce to represent connection between enigine and user.
 *
 * 
 * @author homelleon
 * @see GUI
 *
 */
public interface GUIInterface extends Hideable {
	
	/**
	 * Returns name of user interface object.
	 * 
	 * @return {@link String} value
	 */
	String getName();
	
	/**
	 * Check if user interface is shown.
	 * @return true if user interface is shown<br>
	 * 		   false if user interaface is hidden
	 */
	boolean getIsShown();
	
	/**
	 * Gets list of all user interface texts.
	 * 
	 * @return {@link List}<{@link GUIText>> array of GUI text.
	 */
	List<GUIText> getTexts();
	
	/**
	 * Gets list of all user interface textures.
	 * 
	 * @return {@link List}<{@link GUITexture>> array of GUI text.
	 */
	List<GUITexture> getTextures();
	
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
