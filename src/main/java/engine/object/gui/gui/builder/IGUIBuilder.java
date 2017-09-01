package object.gui.gui.builder;

import object.gui.gui.IGUI;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

/**
 * 
 * @author homelleon
 * @see GUIBuilder
 */
public interface IGUIBuilder {
	
	IGUIBuilder setTexture(String name, GUITexture texture);
	IGUIBuilder setText(String name, GUIText text);
	IGUI build(String name);

}
