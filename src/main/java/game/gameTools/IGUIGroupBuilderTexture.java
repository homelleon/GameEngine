package gameTools;

import object.gui.group.IGUIGroup;

public interface IGUIGroupBuilderTexture {
	
	IGUIGroupBuilderTexture setTextureName(String name);
	IGUIGroup build(String name);
}
