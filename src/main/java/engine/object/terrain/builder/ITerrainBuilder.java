package object.terrain.builder;

import object.terrain.terrain.ITerrain;
import object.texture.terrain.TerrainTexturePack;

public interface ITerrainBuilder {
	
	ITerrainBuilder setXPosition(int x);
	ITerrainBuilder setYPosition(int y);
	ITerrainBuilder setTexturePack(TerrainTexturePack texturePack);
	ITerrainBuilder setBlendTextureName(String name);
	ITerrainBuilder setHeightTextureName(String name);
	ITerrainBuilder setAmplitude(float amplitude);
	ITerrainBuilder setOctaves(int octaves);
	ITerrainBuilder setRoughness(float roughness);
	ITerrain create(String name);

}
