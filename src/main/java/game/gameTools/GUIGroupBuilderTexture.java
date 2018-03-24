package gameTools;

import manager.gui.GUIManager;
import object.gui.group.GUIGroup;
import object.gui.gui.GUIBuilder;

public class GUIGroupBuilderTexture implements IGUIGroupBuilderTexture {
	
	private String textureName;
	private GUIManager guiManager;
	
	public GUIGroupBuilderTexture(GUIManager guiManager) {
		this.guiManager = guiManager;
	}
	
	@Override
	public IGUIGroupBuilderTexture setTextureName(String name) {
		this.textureName = name;
		return this;
	}

	@Override
	public GUIGroup build(String name) {
		GUIBuilder guiBuilder = new GUIBuilder(guiManager.getComponent())
				.setTexture(textureName + "Text",guiManager.getComponent().getTextures().get(textureName));
		GUIGroup group = guiManager.getGroups().createEmpty(name + "GUIGroup");
		group.add(guiBuilder.build(name+"GUI"));
		return group;
	}

}
