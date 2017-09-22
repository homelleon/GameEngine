package object.terrain.terrain.builder;

import object.terrain.terrain.ITerrain;
import object.texture.Texture2D;
import object.texture.terrain.TerrainTexturePack;

public interface ITerrainBuilder {
	
	ITerrainBuilder setXPosition(int x);
	ITerrainBuilder setZPosition(int y);
	ITerrainBuilder setTexturePack(TerrainTexturePack texturePack);
	ITerrainBuilder setBlendTexture(Texture2D texture);
	ITerrainBuilder setHeightTextureName(String name);
	ITerrainBuilder setAmplitude(float amplitude);
	ITerrainBuilder setOctaves(int octaves);
	ITerrainBuilder setRoughness(float roughness);
	ITerrainBuilder setSeed(int seed);
	ITerrain build(String name);

}
