package renderer.object.gui;

import java.util.ArrayList;
import java.util.List;

import object.gui.group.GUIGroupInterface;
import object.gui.manager.GUIManagerInterface;

public class GUIRenderer {
	
	private GUITextureRenderer textureRenderer;
	private GUITextRenderer textRenderer;
	List<GUIGroupInterface> goups;
	
	public GUIRenderer(GUIManagerInterface manager) {
		this.textureRenderer = new GUITextureRenderer(manager.getComponent().getTextures());
		this.textRenderer = new GUITextRenderer(manager.getComponent().getTexts());
		this.goups = new ArrayList<GUIGroupInterface>();
	}
	
	public void render() {
		textureRenderer.render();
		textRenderer.render();
	}

}
