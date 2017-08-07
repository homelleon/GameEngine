package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

import object.gui.gui.IGUI;

/**
 * 
 * @author homelleon
 * @see GUIButtonBase
 */
public class GUIButton extends GUIButtonBase {

	public GUIButton(String name, IGUI gui, Vector2f point1, Vector2f point2) {
		super(name, gui, point1, point2);
	}

}
