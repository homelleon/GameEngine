package object.gui.gui.builder;

import object.gui.gui.IGUI;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

public interface IGUIBuilder {
	
	IGUIBuilder setTexture(GUITexture texture);
	IGUIBuilder setText(GUIText text);
	IGUI build(String name);

}
