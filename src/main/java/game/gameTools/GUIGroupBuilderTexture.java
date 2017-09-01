package gameTools;

import manager.gui.IGUIManager;
import object.gui.group.IGUIGroup;
import object.gui.gui.builder.GUIBuilder;
import object.gui.gui.builder.IGUIBuilder;

public class GUIGroupBuilderTexture implements IGUIGroupBuilderTexture {
	
	private String textureName;
	private IGUIManager guiManager;
	
	public GUIGroupBuilderTexture(IGUIManager guiManager) {
		this.guiManager = guiManager;
	}
	
	@Override
	public IGUIGroupBuilderTexture setTextureName(String name) {
		this.textureName = name;
		return this;
	}

	@Override
	public IGUIGroup build(String name) {
		IGUIBuilder guiBuilder = new GUIBuilder(guiManager.getComponent())
				.setTexture(textureName + "Text",guiManager.getComponent().getTextures().get(textureName));
		IGUIGroup group = guiManager.getGroups().createEmpty(name + "GUIGroup");
		group.add(guiBuilder.build(name+"GUI"));
		return group;
	}

}
