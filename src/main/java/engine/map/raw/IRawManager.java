package map.raw;

import java.util.List;

import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.texture.model.ModelTexture;
import object.texture.terrain.pack.TerrainTexturePack;
import object.texture.terrain.texture.TerrainTexture;

public interface IRawManager {
	
	void addRawModel(RawModel model);
	List<RawModel> getRawModels();
	RawModel getRawModel(String name);
	
	void addModelTexture(ModelTexture texture);
	List<ModelTexture> getModelTextures();
	ModelTexture getModelTexture(String name);
	
	void addTerrainTexturePack(TerrainTexturePack texturePack);
	List<TerrainTexturePack> getTerrainTexturePacks();
	TerrainTexturePack getTerrainTexturePack(String name);
	
	void addTerrainTexture(TerrainTexture terrainTexture);
	List<TerrainTexture> getTerrainTextures();
	TerrainTexture getTerrainTexture(String name);
	
	void addTexturedModel(TexturedModel model);
	List<TexturedModel> getTexturedModels();
	TexturedModel getTexturedModel(String name);
	
	void clean();

}
