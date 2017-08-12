package object.map.raw;

import java.util.List;

import object.model.RawModel;
import object.texture.model.ModelTexture;
import object.texture.terrain.TerrainTexturePack;

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
	
	void clean();

}
