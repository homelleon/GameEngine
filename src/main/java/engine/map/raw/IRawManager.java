package map.raw;

import java.util.List;

import object.texture.Texture2D;
import object.texture.material.Material;
import object.texture.terrain.TerrainTexturePack;
import primitive.model.Mesh;
import primitive.model.Model;

/**
 * 
 * @author homelleon
 * @version 1.0
 * @see RawManager
 */
public interface IRawManager {
	
	void addMeshGroup(Mesh[] meshes);
	List<Mesh[]> getMeshGroups();
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
