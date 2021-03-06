package manager.gui.texture;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.debug.EngineDebug;
import object.gui.texture.GUITexture;

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

	@Override
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

	@Override
	public GUITexture get(String name) {
		if(this.textures.containsKey(name)) {
			return this.textures.get(name);
		} else {
			throw new NullPointerException("There is no texture with name " + name + "in gui texture array!");
		}
	}

	@Override
	public Collection<GUITexture> getAll() {
		return this.textures.values();
	}

	@Override
	public boolean delete(String name) {
		if(this.textures.containsKey(name)) {
			this.textures.remove(name);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void clean() {
		this.textures.clear();
	}

}
