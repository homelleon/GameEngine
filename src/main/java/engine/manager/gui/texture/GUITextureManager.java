package manager.gui.texture;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			guiList.forEach(guiTexture ->
				this.textures.put(guiTexture.getName(), guiTexture));
		}
	}
	


	@Override
	public void addAll(List<GUITexture> guiList) {
		if ((guiList != null) && (!guiList.isEmpty())) {
			guiList.forEach(guiTexture -> 
				this.textures.put(guiTexture.getName(), guiTexture));
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
