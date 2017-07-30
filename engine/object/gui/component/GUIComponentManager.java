package object.gui.component;

import java.util.Collection;

import org.w3c.dom.Document;

import core.settings.EngineSettings;
import object.gui.group.GUIGroupInterface;
import object.gui.text.manager.GUITextManager;
import object.gui.text.manager.GUITextManagerInterface;
import object.gui.texture.manager.GUITextureManager;
import object.gui.texture.manager.GUITextureManagerInterface;
import renderer.object.gui.GUIRenderer;
import renderer.object.gui.GUIRendererInterface;
import tool.xml.loader.XMLFileLoader;
import tool.xml.loader.XMLLoaderInterface;

public class GUIComponentManager implements GUIComponentManagerInterface {

	private GUITextureManagerInterface textureManager;
	private GUITextManagerInterface textManager;
	private GUIRendererInterface guiRenderer;

	public GUIComponentManager(GUITextureManagerInterface textureManager, GUITextManagerInterface textManager) {
		this.textManager = textManager;
		this.textureManager = textureManager;
		this.guiRenderer = new GUIRenderer(textManager.getFonts());
	}

	public GUIComponentManager(String guiFileName) {
		this.textureManager = new GUITextureManager();
		this.textManager = new GUITextManager();
		this.guiRenderer = new GUIRenderer(textManager.getFonts());
		XMLLoaderInterface loader = new XMLFileLoader(EngineSettings.INTERFACE_PATH + guiFileName + EngineSettings.EXTENSION_XML);
		Document document = loader.load();
		textManager.readDocument(document);
		textureManager.readDocument(document);
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
