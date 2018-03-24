package object.gui.element.button;

import object.bounding.BoundingQuad;
import object.gui.group.GUIGroup;

/**
 * 
 * @author homelleon
 * @see AGUIButton
 * @see IGUIButton
 */
public class GUIButton extends AGUIButton {

	public GUIButton(String name, GUIGroup guiGroup) {
		super(name, guiGroup);
	}
	
	public GUIButton(String name, GUIGroup guiGroup, BoundingQuad quad) {
		super(name, guiGroup, quad);
	}

}
