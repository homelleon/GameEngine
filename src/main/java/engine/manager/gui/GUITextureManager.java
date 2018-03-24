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
		if ((guiList != null) && (!guiList.isEmpty())) {
			this.textures.putAll(
					guiList.stream()
						.collect(Collectors.toMap(
								GUITexture::getName, Function.identity())));
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null collection value into GUITextureManager array!");
			}
		}
	}

	public void add(GUITexture guiTexture) {
		if (guiTexture != null) {
			this.textures.put(guiTexture.getName(), guiTexture);
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null value into GUITextureManager array!");
			}
		}
	}

	public GUITexture get(String name) {
		if(this.textures.containsKey(name)) {
			return this.textures.get(name);
		} else {
			throw new NullPointerException("There is no texture with name " + name + "in gui texture array!");
		}
	}

	public Collection<GUITexture> getAll() {
		return this.textures.values();
	}

	public boolean delete(String name) {
		if(this.textures.containsKey(name)) {
			this.textures.remove(name);
			return true;
		} else {
			return false;
		}
	}

	public void clean() {
		this.textures.clear();
	}

}
