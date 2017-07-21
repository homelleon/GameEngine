package object.gui.texture.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.settings.EngineSettings;
import object.gui.texture.GUITexture;
import object.gui.texture.parser.GUITextureParserInterface;
import object.gui.texture.parser.GUITextureXMLParser;
import renderer.object.gui.GUITextureRenderer;
import tool.xml.loader.XMLFileLoader;
import tool.xml.loader.XMLLoaderInterface;

/**
 * Graphic interface manager for controling and storing structured map and
 * arrays of graphic interfaces.
 * 
 * @author homelleon
 * @version 1.0
 */

public class GUITextureManager implements GUITextureManagerInterface {
	
	private GUITextureRenderer renderer;
	private Map<String, GUITexture> textures = new HashMap<String, GUITexture>();
	
	public GUITextureManager() {
		this.renderer = new GUITextureRenderer(this);
	}
	
//	public static List<GUITexture> createGui(Loader loader) {
//		List<GUITexture> guiTextureList = new ArrayList<GUITexture>();
//		
//		GUITexture guiTexture = new GUITexture("Helth", 
//				loader.loadTexture(ES.INTERFACE_TEXTURE_PATH,"helthBar"), 
//				new Vector2f(-0.7f, -0.7f), new Vector2f(0.25f, 0.25f));
//		guiTextureList.add(guiTexture);
//		
//		return guiTextureList;
//	}

	@Override
	public void addAll(Collection<GUITexture> guiList) {
		if((guiList != null) && (!guiList.isEmpty())) {
			for(GUITexture guiTexture : guiList) {
				this.textures.put(guiTexture.getName(), guiTexture);
			}
		}	
	}

	@Override
	public void add(GUITexture guiTexture) {
		if(guiTexture != null) {
			this.textures.put(guiTexture.getName(), guiTexture); 		
		}
	}

	@Override
	public GUITexture get(String name) {
		GUITexture guiTexture = null;
		if(this.textures.containsKey(name)) {
			guiTexture = this.textures.get(name);
		}
		return guiTexture;
	}

	@Override
	public Collection<GUITexture> getAll() {
		return this.textures.values();
	}
	
	@Override
	public void render() {
		this.renderer.render();		
	}

	@Override
	public void clearAll() {
		this.textures.clear();
	}
	
	@Override
	public void readFile(String fileName) {
		XMLLoaderInterface xmlLoader = new XMLFileLoader(EngineSettings.INTERFACE_PATH + fileName + EngineSettings.EXTENSION_XML);
		GUITextureParserInterface parser = new GUITextureXMLParser(xmlLoader.load());
		this.addAll(parser.parse());		
	}

}
