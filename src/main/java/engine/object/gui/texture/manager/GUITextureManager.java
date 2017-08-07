package object.gui.texture.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import core.settings.EngineSettings;
import object.gui.texture.GUITexture;
import object.gui.texture.parser.GUITextureXMLParser;
import tool.xml.loader.XMLFileLoader;
import tool.xml.loader.IXMLLoader;
import tool.xml.parser.IListParser;

/**
 * Graphic interface manager for controling and storing structured map and
 * arrays of graphic interfaces.
 * 
 * @author homelleon
 * @version 1.0
 * @see IGUITextureManager
 */

public class GUITextureManager implements IGUITextureManager {

	private Map<String, GUITexture> textures = new HashMap<String, GUITexture>();

	@Override
	public void addAll(Collection<GUITexture> guiList) {
		if ((guiList != null) && (!guiList.isEmpty())) {
			for (GUITexture guiTexture : guiList) {
				this.textures.put(guiTexture.getName(), guiTexture);
			}
		}
	}

	@Override
	public void add(GUITexture guiTexture) {
		if (guiTexture != null) {
			this.textures.put(guiTexture.getName(), guiTexture);
		}
	}

	@Override
	public GUITexture get(String name) {
		GUITexture guiTexture = null;
		if (this.textures.containsKey(name)) {
			guiTexture = this.textures.get(name);
		}
		return guiTexture;
	}

	@Override
	public Collection<GUITexture> getAll() {
		return this.textures.values();
	}

	@Override
	public void cleanUp() {
		this.textures.clear();
	}

	@Override
	public void readFile(String fileName) {
		IXMLLoader xmlLoader = new XMLFileLoader(
				EngineSettings.INTERFACE_PATH + fileName + EngineSettings.EXTENSION_XML);
		IListParser<GUITexture> parser = new GUITextureXMLParser(xmlLoader.load());
		this.addAll(parser.parse());
	}

	@Override
	public void readDocument(Document document) {
		IListParser<GUITexture> parser = new GUITextureXMLParser(document);
		this.addAll(parser.parse());		
	}

}
