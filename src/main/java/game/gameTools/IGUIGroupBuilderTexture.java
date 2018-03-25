package gameTools;

import object.gui.GUIGroup;

public interface IGUIGroupBuilderTexture {
	
	IGUIGroupBuilderTexture setTextureName(String name);
	GUIGroup build(String name);
}
