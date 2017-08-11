package object.map.raw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.model.RawModel;
import object.texture.Texture;

public class RawManager implements IRawManager {
	
	private Map<String, RawModel> models = new HashMap<String, RawModel>();
	private Map<String, Texture> textures = new HashMap<String, Texture>();

	@Override
	public List<RawModel> getRawModels() {
		List<RawModel> list = new ArrayList<RawModel>();
		list.addAll(this.models.values());
		return list;
	}

	@Override
	public List<Texture> getTextures() {
		List<Texture> list = new ArrayList<Texture>();
		list.addAll(this.textures.values());
		return list;
	}

	@Override
	public void clean() {
		this.models.clear();
		this.textures.clear();
	}

}
