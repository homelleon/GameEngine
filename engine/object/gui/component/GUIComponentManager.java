package object.gui.component;

import java.util.Collection;

import object.gui.group.GUIGroupInterface;
import object.gui.text.manager.GUITextManager;
import object.gui.text.manager.GUITextManagerInterface;
import object.gui.texture.manager.GUITextureManager;
import object.gui.texture.manager.GUITextureManagerInterface;
import renderer.object.gui.GUIRenderer;
import renderer.object.gui.GUIRendererInterface;

public class GUIComponentManager implements GUIComponentManagerInterface {

	private GUITextureManagerInterface textureManager;
	private GUITextManagerInterface textManager;
	private GUIRendererInterface guiRenderer;

	public GUIComponentManager(GUITextureManagerInterface textureManager, GUITextManagerInterface textManager) {
		this.textManager = textManager;
		this.textureManager = textureManager;
		this.guiRenderer = new GUIRenderer(textManager.getFonts());
	}

	public GUIComponentManager(String textureFileName, String textFileName) {
		this.textureManager = new GUITextureManager();
		this.textManager = new GUITextManager();
		this.guiRenderer = new GUIRenderer(textManager.getFonts());
		textManager.readFile(textFileName);
		textureManager.readFile(textureFileName);
	}

	@Override
	public GUITextureManagerInterface getTextures() {
		return textureManager;
	}

	@Override
	public GUITextManagerInterface getTexts() {
		return textManager;
	}

	@Override
	public void render(Collection<GUIGroupInterface> groups) {
		this.guiRenderer.render(groups);
	}

	@Override
	public void cleanAll() {
		this.guiRenderer.cleanUp();
		this.textManager.cleanUp();
		this.textureManager.cleanUp();
	}

}
