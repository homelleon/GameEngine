package object.terrain.terrain.builder;

import object.terrain.terrain.ITerrain;
import object.texture.Texture2D;

public interface ITerrainBuilder {
	
	ITerrainBuilder setSize(int size);
	ITerrainBuilder setXPosition(int x);
	ITerrainBuilder setZPosition(int y);
	ITerrainBuilder setHeightMap(Texture2D heightMap);
	ITerrainBuilder setAmplitude(float amplitude);
	ITerrainBuilder setOctaves(int octaves);
	ITerrainBuilder setRoughness(float roughness);
	ITerrain build(String name);

}
