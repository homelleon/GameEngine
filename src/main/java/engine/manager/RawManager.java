package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import primitive.model.Mesh;
import primitive.model.Model;
import primitive.texture.Texture2D;
import primitive.texture.material.Material;
import primitive.texture.terrain.TerrainTexturePack;

public class RawManager {
	
	private Map<String, Mesh[]> meshGroups = new HashMap<String, Mesh[]>();
	private Map<String, Texture2D> textures = new HashMap<String, Texture2D>();
	private Map<String, Material> materials = new HashMap<String, Material>();
	private Map<String, TerrainTexturePack> terrainTexturePacks = new HashMap<String, TerrainTexturePack>();
	private Map<String, Model[]> modelGroups = new HashMap<String, Model[]>();

	public void addMeshGroup(Mesh[] meshes) {
		meshGroups.put(meshes[0].getName(), meshes);		
	}

	public List<Mesh[]> getMeshGroups() {
		List<Mesh[]> list = new ArrayList<Mesh[]>();
		list.addAll(meshGroups.values());
		return list;
	}
	
	public Mesh[] getMeshGroup(String name) {
		if (!meshGroups.containsKey(name)) {
			throw new NullPointerException("There is no mesh with name " + name + " in mesh array!");
		}
		return meshGroups.get(name);
	}

	public void addMaterial(Material texture) {
		materials.put(texture.getName(), texture);		
	}

	public List<Material> getMaterials() {
		List<Material> list = new ArrayList<Material>();
		list.addAll(this.materials.values());
		return list;
	}

	public Material getMaterial(String name) {
		if(this.materials.containsKey(name)) {
			return this.materials.get(name);
		} else {
			throw new NullPointerException("There is no texture with name " + name + " in texture array!");
		}
	}

	public void addTerrainTexturePack(TerrainTexturePack texturePack) {
		this.terrainTexturePacks.put(texturePack.getName(), texturePack);		
	}

	public List<TerrainTexturePack> getTerrainTexturePacks() {
		List<TerrainTexturePack> list = new ArrayList<TerrainTexturePack>();
		list.addAll(this.terrainTexturePacks.values());
		return list;
	}

	public TerrainTexturePack getTerrainTexturePack(String name) {
		if(this.terrainTexturePacks.containsKey(name)) {
			return this.terrainTexturePacks.get(name);
		} else {
			throw new NullPointerException("There is no texture pack with name " + name + " in terrain texture pack array!");
		}
	}

	public void addModelGroup(Model[] models) {
		this.modelGroups.put(models[0].getName(), models);		
	}

	public List<Model[]> getModelGroups() {
		List<Model[]> list = new ArrayList<Model[]>();
		list.addAll(this.modelGroups.values());
		return list;
	}

	public Model[] getModelGroup(String name) {
		if(this.modelGroups.containsKey(name)) {
			return this.modelGroups.get(name);
		} else {
			throw new NullPointerException("There is no textured model with name " + name + " in textured model array!");
		}
	}

	public void clean() {
		this.meshGroups.clear();
		this.materials.clear();
		this.terrainTexturePacks.clear();
		this.modelGroups.clear();
	}

	public void addTexture(Texture2D texture) {
		this.textures.put(texture.getName(), texture);
	}

	public List<Texture2D> getTextures() {
		List<Texture2D> list = new ArrayList<Texture2D>();
		list.addAll(textures.values());
		return list;
	}

	public Texture2D getTexture(String name) {
		if(this.textures.containsKey(name)) {
			return this.textures.get(name);
		} else {
			throw new NullPointerException("There is no texture with name " + name + " in texture array!");
		}
	}


}
