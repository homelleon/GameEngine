package object.gui.pattern.manager;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import object.gui.pattern.button.IGUIButton;
import tool.manager.IManager;

/**
 * Manages graphic user interface buttons.
 * 
 * @author homelleon
 *
 */
public interface IGUIButtonManager extends IManager<IGUIButton> {

	/**
	 * Gets list of buttons selected by mouse cursor.
	 * 
	 * @param mouseCoord
	 *            - {@link Vector2f} value of mouse cursor coordinates
	 * @return {@link List}<{@link IGUIButton}> value of buttons array
	 */
	List<IGUIButton> getMouseOverButton(Vector2f mouseCoord);

}
