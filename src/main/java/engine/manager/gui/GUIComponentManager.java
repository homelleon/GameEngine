package manager.gui;

import java.util.Collection;

import org.w3c.dom.Document;

import core.settings.EngineSettings;
import object.gui.group.GUIGroup;
import object.gui.text.GUIText;
import object.gui.text.GUITextReader;
import object.gui.texture.GUITexture;
import object.gui.texture.GUITextureReader;
import renderer.gui.GUIRenderer;
import tool.xml.loader.IXMLLoader;
import tool.xml.loader.XMLFileLoader;
import tool.xml.reader.IXMLReader;

/**
 * Manages GUI components such as textureManager, textManager and its renderer.
 * 
 * @author homelleon
 */
public class GUIComponentManager {

	private GUITextureManager textureManager;
	private GUITextManager textManager;
	private GUIRenderer guiRenderer;

	/**
	 * Constructor to create component manager with input texture and text managers.
	 * 
	 * @param textureManager {@link IGUITextureManager} object
	 * @param textManager {@link IGUITextManager} object
	 */
	public GUIComponentManager(GUITextureManager textureManager, GUITextManager textManager) {
		this.textManager = textManager;
		this.textureManager = textureManager;
		this.guiRenderer = new GUIRenderer(this.textManager.getFonts());
	}

	/**
	 * Constructor to create component manager with inpute file name to load parameters from.
	 * 
	 * @param guiFileName {@link String} value of file name
	 */
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

	public GUITextureManager getTextures() {
		return textureManager;
	}

	public GUITextManager getTexts() {
		return textManager;
	}

	public void render(Collection<GUIGroup> groups) {
		this.guiRenderer.render(groups);
	}

	public void cleanAll() {
		this.guiRenderer.cleanUp();
		this.textManager.clean();
		this.textureManager.clean();
	}

}
