package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

import object.gui.gui.GUIInterface;

/**
 * 
 * @author homelleon
 * @see GUIButtonBase
 */
public class GUIButton extends GUIButtonBase {

	public GUIButton(String name, GUIInterface gui, Vector2f point1, Vector2f point2) {
		super(name, gui, point1, point2);
	}

}
