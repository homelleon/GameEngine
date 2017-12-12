package primitive.texture.terrain.builder;

import primitive.texture.terrain.TerrainTexturePack;

public interface ITerrainTexturePackBuilder {
	
	ITerrainTexturePackBuilder setBackgroundTexture(String textureName);
	ITerrainTexturePackBuilder setRedTexture(String textureName);
	ITerrainTexturePackBuilder setGreenTexture(String textureName);
	ITerrainTexturePackBuilder setBlueTexture(String textureName);
	TerrainTexturePack create(String name);

}
