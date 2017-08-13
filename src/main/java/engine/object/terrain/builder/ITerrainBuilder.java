package object.terrain.builder;

import object.terrain.terrain.ITerrain;
import object.texture.terrain.pack.TerrainTexturePack;
import object.texture.terrain.texture.TerrainTexture;

public interface ITerrainBuilder {
	
	ITerrainBuilder setXPosition(int x);
	ITerrainBuilder setZPosition(int y);
	ITerrainBuilder setTexturePack(TerrainTexturePack texturePack);
	ITerrainBuilder setBlendTexture(TerrainTexture texture);
	ITerrainBuilder setHeightTextureName(String name);
	ITerrainBuilder setAmplitude(float amplitude);
	ITerrainBuilder setOctaves(int octaves);
	ITerrainBuilder setRoughness(float roughness);
	ITerrain create(String name);

}
