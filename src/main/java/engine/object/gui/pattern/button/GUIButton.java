package object.gui.pattern.button;

import org.lwjgl.util.vector.Vector2f;

import object.gui.group.IGUIGroup;

/**
 * 
 * @author homelleon
 * @see GUIButtonBase
 * @see IGUIButton
 */
public class GUIButton extends GUIButtonBase {

	public GUIButton(String name, IGUIGroup guiGroup, Vector2f point1, Vector2f point2) {
		super(name, guiGroup, point1, point2);
	}

}
