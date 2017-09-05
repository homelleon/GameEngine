package object.gui.pattern.button;

import object.bounding.BoundingQuad;
import object.gui.group.IGUIGroup;

/**
 * 
 * @author homelleon
 * @see GUIButtonBase
 * @see IGUIButton
 */
public class GUIButton extends GUIButtonBase {

	public GUIButton(String name, IGUIGroup guiGroup) {
		super(name, guiGroup);
	}
	
	public GUIButton(String name, IGUIGroup guiGroup, BoundingQuad quad) {
		super(name, guiGroup, quad);
	}

}
