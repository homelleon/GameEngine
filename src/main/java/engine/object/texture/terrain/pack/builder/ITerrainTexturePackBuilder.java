package object.texture.terrain.pack.builder;

import object.texture.terrain.pack.TerrainTexturePack;

public interface ITerrainTexturePackBuilder {
	
	ITerrainTexturePackBuilder setBackgroundTexture(String textureName);
	ITerrainTexturePackBuilder setRedTexture(String textureName);
	ITerrainTexturePackBuilder setGreenTexture(String textureName);
	ITerrainTexturePackBuilder setBlueTexture(String textureName);
	TerrainTexturePack create(String name);

}
