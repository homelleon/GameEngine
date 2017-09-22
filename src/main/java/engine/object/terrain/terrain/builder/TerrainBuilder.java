package object.terrain.terrain.builder;

import object.terrain.terrain.ITerrain;
import object.texture.Texture2D;
import object.texture.terrain.TerrainTexturePack;

public abstract class TerrainBuilder {
	
	protected int gridX = 0;
	protected int gridZ = 0;
	protected TerrainTexturePack texturePack;
	protected Texture2D blendTexture;
	protected String heightTextureName;
	protected float amplitude = 0;
	protected int octaves = 0;
	protected float roughness = 0;
	protected int seed;
	
	public ITerrainBuilder setXPosition(int x) {
		this.gridX = x;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setZPosition(int z) {
		this.gridZ = z;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setTexturePack(TerrainTexturePack texturePack) {
		this.texturePack = texturePack;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setBlendTexture(Texture2D texture) {
		this.blendTexture = texture;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setHeightTextureName(String name) {
		this.heightTextureName = name;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setAmplitude(float amplitude) {
		this.amplitude = amplitude;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setOctaves(int octaves) {
		this.octaves = octaves;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setRoughness(float roughness) {
		this.roughness = roughness;
		return (ITerrainBuilder) this;
	}
	
	public  ITerrainBuilder setSeed(int seed) {
		this.seed = seed;
		return (ITerrainBuilder) this;
	}
	
	public abstract ITerrain build(String name);
	
}
