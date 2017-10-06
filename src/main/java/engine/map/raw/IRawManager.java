package map.raw;

import java.util.List;

import manager.primitive.IMeshManager;
import object.texture.Texture2D;
import object.texture.material.Material;
import object.texture.terrain.TerrainTexturePack;
import primitive.model.Mesh;
import primitive.model.Model;

/**
 * Provides access for managing raw meshes, models, textures and materials.
 * 
 * @author homelleon
 * @version 1.0
 * @see RawManager
 */
public interface IRawManager {
	
	/**
	 * Adds mesh array.
	 * 
	 * @param meshes {@link Mesh} array
	 */
	void addMeshGroup(Mesh[] meshes);
	
	/**
	 * Gets list of mesh arrays.
	 * 
	 * @return {@link List}<{@link Mesh}[]> - list of mesh arrays
	 */
	List<Mesh[]> getMeshGroups();
	
	/**
	 * Gets mesh array with defined name.
	 * 
	 * @param name {@link String} value of mesh array
	 * 
	 * @return {@link Mesh} array
	 */
	Mesh[] getMeshGroup(String name);
	
	void addTexture(Texture2D texture);
	List<Texture2D> getTextures();
	Texture2D getTexture(String name);
	
	void addMaterial(Material texture);
	List<Material> getMaterials();
	Material getMaterial(String name);
	
	void addTerrainTexturePack(TerrainTexturePack texturePack);
	List<TerrainTexturePack> getTerrainTexturePacks();
	TerrainTexturePack getTerrainTexturePack(String name);
	
	void addModelGroup(Model[] models);
	List<Model[]> getModelGroups();
	Model[] getModelGroup(String name);
	
	void clean();

}
