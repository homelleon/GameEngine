package guis.guiTextures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import core.settings.ES;
import renderers.Loader;

/**
 * Graphic interface manager for controling and storing structured map and
 * arrays of graphic interfaces.
 * 
 * @author homelleon
 * @version 1.0
 */

public class GUITextureManager implements GUITextureManagerInterface {
	
	Map<String, GUITexture> guiTextures = new HashMap<String, GUITexture>();
	
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
				this.guiTextures.put(guiTexture.getName(), guiTexture);
			}
		}	
	}

	@Override
	public void add(GUITexture guiTexture) {
		if(guiTexture != null) {
			this.guiTextures.put(guiTexture.getName(), guiTexture); 		
		}
	}

	@Override
	public GUITexture getByName(String name) {
		GUITexture guiTexture = null;
		if(this.guiTextures.containsKey(name)) {
			guiTexture = this.guiTextures.get(name);
		}
		return guiTexture;
	}

	@Override
	public Collection<GUITexture> getAll() {
		return this.guiTextures.values();
	}

	@Override
	public void clearAll() {
		this.guiTextures.clear();
	}

}
