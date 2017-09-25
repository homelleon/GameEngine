package map.raw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.texture.Texture2D;
import object.texture.material.Material;
import object.texture.terrain.TerrainTexturePack;
import primitive.model.Mesh;
import primitive.model.Model;

public class RawManager implements IRawManager {
	
	private Map<String, Mesh> meshes = new HashMap<String, Mesh>();
	private Map<String, Texture2D> textures = new HashMap<String, Texture2D>();
	private Map<String, Material> materials = new HashMap<String, Material>();
	private Map<String, TerrainTexturePack> terrainTexturePacks = new HashMap<String, TerrainTexturePack>();
	private Map<String, Model> models = new HashMap<String, Model>();

	@Override
	public void addMesh(Mesh mesh) {
		this.meshes.put(mesh.getName(), mesh);		
	}

	@Override
	public List<Mesh> getMeshes() {
		List<Mesh> list = new ArrayList<Mesh>();
		list.addAll(this.meshes.values());
		return list;
	}
	

	@Override
	public Mesh getMesh(String name) {
		if(this.meshes.containsKey(name)) {
		return this.meshes.get(name);
		} else {
			throw new NullPointerException("There is no mesh with name " + name + " in mesh array!");
		}
	}


	@Override
	public void addMaterial(Material texture) {
		this.materials.put(texture.getName(), texture);		
	}

	@Override
	public List<Material> getMaterials() {
		List<Material> list = new ArrayList<Material>();
		list.addAll(this.materials.values());
		return list;
	}

	@Override
	public Material getMaterial(String name) {
		if(this.materials.containsKey(name)) {
			return this.materials.get(name);
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
	public void addModel(Model model) {
		this.models.put(model.getName(), model);		
	}

	@Override
	public List<Model> getModels() {
		List<Model> list = new ArrayList<Model>();
		list.addAll(this.models.values());
		return list;
	}

	@Override
	public Model getModel(String name) {
		if(this.models.containsKey(name)) {
			return this.models.get(name);
		} else {
			throw new NullPointerException("There is no textured model with name " + name + " in textured model array!");
		}
	}

	@Override
	public void clean() {
		this.meshes.clear();
		this.materials.clear();
		this.terrainTexturePacks.clear();
		this.models.clear();
	}

	@Override
	public void addTexture(Texture2D texture) {
		this.textures.put(texture.getName(), texture);
	}

	@Override
	public List<Texture2D> getTextures() {
		List<Texture2D> list = new ArrayList<Texture2D>();
		list.addAll(textures.values());
		return list;
	}

	@Override
	public Texture2D getTexture(String name) {
		if(this.textures.containsKey(name)) {
			return this.textures.get(name);
		} else {
			throw new NullPointerException("There is no texture with name " + name + " in texture array!");
		}
	}


}
