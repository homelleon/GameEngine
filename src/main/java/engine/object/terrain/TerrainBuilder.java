package object.terrain;

import object.terrain.generator.TerrainGenerator;
import primitive.texture.Texture2D;
import tool.math.vector.Vector2f;

public class TerrainBuilder {
	
	private int size = 128;
	private int gridX = 0;
	private int gridZ = 0;
	private Texture2D heightMap;
	private float amplitude = 0;
	private int octaves = 0;
	private float roughness = 0;

	public TerrainBuilder setHeightMap(Texture2D heightMap) {
		this.heightMap = heightMap;
		return this;
	}
	
	public TerrainBuilder setXPosition(int x) {
		gridX = x;
		return this;
	}
	
	public TerrainBuilder setZPosition(int z) {
		gridZ = z;
		return this;
	}
	
	public TerrainBuilder setAmplitude(float amplitude) {
		this.amplitude = amplitude;
		return this;
	}
	
	public TerrainBuilder setOctaves(int octaves) {
		this.octaves = octaves;
		return this;
	}
	
	public TerrainBuilder setRoughness(float roughness) {
		this.roughness = roughness;
		return this;
	}

	public TerrainBuilder setSize(int size) {
		this.size = size;
		return this;
	}
	
	public Terrain build(String name) {
			TerrainGenerator generator = (heightMap != null) ?
				new TerrainGenerator(size, new Vector2f(gridX, gridZ), heightMap) :
				new TerrainGenerator(size, new Vector2f(gridX, gridZ), amplitude, octaves, roughness);
			return generator.generate(name);
	}
	
}
