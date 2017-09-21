package object.gui.gui;

import java.util.List;

import object.Moveable;
import object.gui.Hideable;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;
import tool.math.vector.Vector2f;

/**
 * User interafce to represent connection between enigine and user.
 *
 * 
 * @author homelleon
 * @see GUI
 *
 */
public interface IGUI extends Hideable, Moveable<Vector2f> {

	/**
	 * Returns name of user interface object.
	 * 
	 * @return {@link String} value
	 */
	String getName();

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
	 * @param value
	 *            {@link Float}
	 */
	void setTransparency(float value);

	/**
	 * Clear user interface.
	 */
	void clean();

}
