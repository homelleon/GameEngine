package object.map.raw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.texture.model.ModelTexture;
import object.texture.terrain.pack.TerrainTexturePack;
import object.texture.terrain.texture.TerrainTexture;

public class RawManager implements IRawManager {
	
	private Map<String, RawModel> models = new HashMap<String, RawModel>();
	private Map<String, ModelTexture> modelTextures = new HashMap<String, ModelTexture>();
	private Map<String, TerrainTexture> terrainTextures = new HashMap<String, TerrainTexture>();
	private Map<String, TerrainTexturePack> terrainTexturePacks = new HashMap<String, TerrainTexturePack>();
	private Map<String, TexturedModel> texturedModels = new HashMap<String, TexturedModel>();

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
	public void addTerrainTexture(TerrainTexture terrainTexture) {
		this.terrainTextures.put(terrainTexture.getName(), terrainTexture);
	}

	@Override
	public List<TerrainTexture> getTerrainTextures() {
		List<TerrainTexture> list = new ArrayList<TerrainTexture>();
		list.addAll(this.terrainTextures.values());
		return list;
	}

	@Override
	public TerrainTexture getTerrainTexture(String name) {
		if(this.terrainTextures.containsKey(name)) {
			return this.terrainTextures.get(name);
		} else {
			throw new NullPointerException("There is no terrain texture with name " + name + " in terrain texture array!");
		}
	}
	


	@Override
	public void addTexturedModel(TexturedModel model) {
		this.texturedModels.put(model.getName(), model);		
	}

	@Override
	public List<TexturedModel> getTexturedModels() {
		List<TexturedModel> list = new ArrayList<TexturedModel>();
		list.addAll(this.texturedModels.values());
		return list;
	}

	@Override
	public TexturedModel getTexturedModel(String name) {
		if(this.texturedModels.containsKey(name)) {
			return this.texturedModels.get(name);
		} else {
			throw new NullPointerException("There is no textured model with name " + name + " in textured model array!");
		}
	}

	@Override
	public void clean() {
		this.models.clear();
		this.modelTextures.clear();
		this.terrainTexturePacks.clear();
		this.terrainTextures.clear();
		this.texturedModels.clear();
	}


}
