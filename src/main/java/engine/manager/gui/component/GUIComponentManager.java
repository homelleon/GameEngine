package manager.gui.component;

import java.util.Collection;

import org.w3c.dom.Document;

import core.settings.EngineSettings;
import manager.gui.text.GUITextManager;
import manager.gui.text.IGUITextManager;
import manager.gui.texture.GUITextureManager;
import manager.gui.texture.IGUITextureManager;
import object.gui.group.IGUIGroup;
import object.gui.text.GUIText;
import object.gui.text.reader.GUITextReader;
import object.gui.texture.GUITexture;
import object.gui.texture.reader.GUITextureReader;
import renderer.object.gui.GUIRenderer;
import renderer.object.gui.IGUIRenderer;
import tool.xml.loader.IXMLLoader;
import tool.xml.loader.XMLFileLoader;
import tool.xml.reader.IXMLReader;

public class GUIComponentManager implements IGUIComponentManager {

	private IGUITextureManager textureManager;
	private IGUITextManager textManager;
	private IGUIRenderer guiRenderer;

	public GUIComponentManager(IGUITextureManager textureManager, IGUITextManager textManager) {
		this.textManager = textManager;
		this.textureManager = textureManager;
		this.guiRenderer = new GUIRenderer(this.textManager.getFonts());
	}

	public GUIComponentManager(String guiFileName) {
		this.textureManager = new GUITextureManager();
		this.textManager = new GUITextManager();
		this.guiRenderer = new GUIRenderer(this.textManager.getFonts());
		IXMLLoader loader = new XMLFileLoader(EngineSettings.INTERFACE_PATH + guiFileName + EngineSettings.EXTENSION_XML);
		Document document = loader.load();
		IXMLReader<GUITexture> textureReader = new GUITextureReader();
		textureManager.addAll(textureReader.readDocument(document));
		IXMLReader<GUIText> textReader = new GUITextReader();
		textManager.addAll(textReader.readDocument(document));
	}

	@Override
	public IGUITextureManager getTextures() {
		return textureManager;
	}

	@Override
	public IGUITextManager getTexts() {
		return textManager;
	}

	@Override
	public void render(Collection<IGUIGroup> groups) {
		this.guiRenderer.render(groups);
	}

	@Override
	public void cleanAll() {
		this.guiRenderer.cleanUp();
		this.textManager.clean();
		this.textureManager.clean();
	}

}
