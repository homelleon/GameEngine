package object.gui.gui;

import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

public interface IGUIBuilder {
	
	IGUIBuilder setTexture(GUITexture texture);
	IGUIBuilder setText(GUIText text);
	IGUI getGUI(String name);

}
