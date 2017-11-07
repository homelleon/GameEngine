package object.terrain.terrain.builder;

import object.terrain.generator.TerrainGenerator;
import object.terrain.terrain.ITerrain;
import object.texture.Texture2D;
import tool.math.vector.Vector2f;

public class TerrainBuilder implements ITerrainBuilder {
	
	private int size = 128;
	private int gridX = 0;
	private int gridZ = 0;
	private Texture2D heightMap;
	private float amplitude = 0;
	private int octaves = 0;
	private float roughness = 0;

	@Override
	public ITerrainBuilder setHeightMap(Texture2D heightMap) {
		this.heightMap = heightMap;
		return this;
	}
	
	public ITerrainBuilder setXPosition(int x) {
		this.gridX = x;
		return this;
	}
	
	public ITerrainBuilder setZPosition(int z) {
		this.gridZ = z;
		return this;
	}
	
	public ITerrainBuilder setAmplitude(float amplitude) {
		this.amplitude = amplitude;
		return this;
	}
	
	public ITerrainBuilder setOctaves(int octaves) {
		this.octaves = octaves;
		return this;
	}
	
	public ITerrainBuilder setRoughness(float roughness) {
		this.roughness = roughness;
		return this;
	}

	@Override
	public ITerrainBuilder setSize(int size) {
		this.size = size;
		return this;
	}
	
	@Override
	public ITerrain build(String name) {
			TerrainGenerator generator;
			if(heightMap != null) {
				generator = new TerrainGenerator(size, new Vector2f(gridX, gridZ), heightMap);
			} else {
				generator = new TerrainGenerator(size, new Vector2f(gridX, gridZ), amplitude, octaves, roughness);
			}
			return generator.generate(name);
	}
	
}
