package gameTools;

import object.gui.group.GUIGroup;

public interface IGUIGroupBuilderTexture {
	
	IGUIGroupBuilderTexture setTextureName(String name);
	GUIGroup build(String name);
}
