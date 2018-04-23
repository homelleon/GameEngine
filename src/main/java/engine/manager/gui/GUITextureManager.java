package manager.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.EngineDebug;
import object.gui.texture.GUITexture;

/**
 * Manages graphic user interface textures.
 * 
 * @author homelleon
 * @version 1.0
 * 
 * @see IGUITextureManager
 * @see GUITexture
 */

public class GUITextureManager {

	private Map<String, GUITexture> textures = new HashMap<String, GUITexture>();

	public void addAll(Collection<GUITexture> guiList) {
		if (guiList == null || guiList.isEmpty()) {
			if (EngineDebug.hasDebugPermission()) {
				System.err.println("Trying to add null collection value into GUITextureManager array!");
			}
			return;
		}
		
		textures.putAll(
				guiList.stream()
					.collect(Collectors.toMap(GUITexture::getName, Function.identity()))
			);
	}

	public void add(GUITexture guiTexture) {
		if (guiTexture == null) {
			if(EngineDebug.hasDebugPermission())
				System.err.println("Trying to add null value into GUITextureManager array!");
			return;
		}
		textures.put(guiTexture.getName(), guiTexture);
	}

	public GUITexture get(String name) {
		if (!textures.containsKey(name))
			throw new NullPointerException("There is no texture with name " + name + "in gui texture array!");
		
		return textures.get(name);
	}

	public Collection<GUITexture> getAll() {
		return textures.values();
	}

	public boolean delete(String name) {
		if (!textures.containsKey(name)) return false;
		textures.remove(name);
		return true;
	}

	public void clean() {
		textures.clear();
	}

}
