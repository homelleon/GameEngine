package object.gui.element.button;

import object.bounding.BoundingQuad;
import object.gui.group.IGUIGroup;

/**
 * 
 * @author homelleon
 * @see AGUIButton
 * @see IGUIButton
 */
public class GUIButton extends AGUIButton {

	public GUIButton(String name, IGUIGroup guiGroup) {
		super(name, guiGroup);
	}
	
	public GUIButton(String name, IGUIGroup guiGroup, BoundingQuad quad) {
		super(name, guiGroup, quad);
	}

}
