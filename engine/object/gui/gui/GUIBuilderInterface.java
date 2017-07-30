package object.gui.gui;

import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

public interface GUIBuilderInterface {
	
	GUIBuilderInterface setTexture(GUITexture texture);
	GUIBuilderInterface setText(GUIText text);
	GUIInterface getGUI(String name);

}
