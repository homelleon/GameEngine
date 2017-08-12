package object.terrain.builder;

import object.terrain.terrain.ITerrain;
import object.texture.terrain.pack.TerrainTexturePack;

public abstract class TerrainBuilder {
	
	protected int gridX = 0;
	protected int gridZ = 0;
	protected TerrainTexturePack texturePack = null;
	protected String blendTextureName = null;
	protected String heightTextureName = null;
	protected float amplitude = 0;
	protected int octaves = 0;
	protected float roughness = 0;
	
	public ITerrainBuilder setXPosition(int x) {
		this.gridX = x;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setYPosition(int y) {
		this.gridZ = y;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setTexturePack(TerrainTexturePack texturePack) {
		this.texturePack = texturePack;
		return (ITerrainBuilder) this;
	}
	
	public ITerrainBuilder setBlendTextureName(String name) {
		this.blendTextureName = name;
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
	
	public abstract ITerrain create(String name);
	
}
