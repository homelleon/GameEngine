package object.map.raw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.model.RawModel;
import object.texture.model.ModelTexture;
import object.texture.terrain.TerrainTexturePack;

public class RawManager implements IRawManager {
	
	private Map<String, RawModel> models = new HashMap<String, RawModel>();
	private Map<String, ModelTexture> modelTextures = new HashMap<String, ModelTexture>();
	private Map<String, TerrainTexturePack> terrainTexturePacks = new HashMap<String, TerrainTexturePack>();

	@Override
	public void addRawModel(RawModel model) {
		this.models.put(model.getName(), model);		
	}

	@Override
	public List<RawModel> getRawModels() {
		List<RawModel> list = new ArrayList<RawModel>();
		list.addAll(this.models.values());
		return list;
	}
	

	@Override
	public RawModel getRawModel(String name) {
		if(this.models.containsKey(name)) {
		return this.models.get(name);
		} else {
			throw new NullPointerException("There is no model with name " + name + " in model array!");
		}
	}


	@Override
	public void addModelTexture(ModelTexture texture) {
		this.modelTextures.put(texture.getName(), texture);		
	}

	@Override
	public List<ModelTexture> getModelTextures() {
		List<ModelTexture> list = new ArrayList<ModelTexture>();
		list.addAll(this.modelTextures.values());
		return list;
	}

	@Override
	public ModelTexture getModelTexture(String name) {
		if(this.modelTextures.containsKey(name)) {
			return this.modelTextures.get(name);
		} else {
			throw new NullPointerException("There is no texture with name " + name + " in texture array!");
		}
	}


	@Override
	public void addTerrainTexturePack(TerrainTexturePack texturePack) {
		this.terrainTexturePacks.put(texturePack.getName(), texturePack);		
	}


	@Override
	public List<TerrainTexturePack> getTerrainTexturePacks() {
		List<TerrainTexturePack> list = new ArrayList<TerrainTexturePack>();
		list.addAll(this.terrainTexturePacks.values());
		return list;
	}


	@Override
	public TerrainTexturePack getTerrainTexturePack(String name) {
		if(this.terrainTexturePacks.containsKey(name)) {
			return this.terrainTexturePacks.get(name);
		} else {
			throw new NullPointerException("There is no texture pack with name " + name + " in terrain texture pack array!");
		}
	}

	@Override
	public void clean() {
		this.models.clear();
		this.modelTextures.clear();
		this.terrainTexturePacks.clear();
	}


}
