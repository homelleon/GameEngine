package object.texture.material;

import object.texture.Texture2D;
import object.texture.terrain.TerrainTexturePack;

public class TerrainMaterial {
	
	private String name;
	private TerrainTexturePack texturePack;
	private Texture2D heightMap;
	private Texture2D normalMap;
	private Texture2D blendMap;
	
	public TerrainMaterial(String name) {
		this.name = name;
	}

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public TerrainMaterial setTexturePack(TerrainTexturePack texturePack) {
		this.texturePack = texturePack;
		return this;
	}

	public Texture2D getHeightMap() {
		return heightMap;
	}

	public TerrainMaterial setHeightMap(Texture2D heightMap) {
		this.heightMap = heightMap;
		return this;
	}

	public Texture2D getNormalMap() {
		return normalMap;
	}

	public TerrainMaterial setNormalMap(Texture2D normalMap) {
		this.normalMap = normalMap;
		return this;
	}

	public Texture2D getBlendMap() {
		return blendMap;
	}

	public TerrainMaterial setBlendMap(Texture2D blendMap) {
		this.blendMap = blendMap;
		return this;
	}

	public String getName() {
		return name;
	}
	
	public TerrainMaterial clone(String name) {
		TerrainMaterial material = new TerrainMaterial(name);
		material.setTexturePack(texturePack);
		material.setHeightMap(heightMap);
		material.setNormalMap(normalMap);
		material.setBlendMap(blendMap);
		return material;
	}

}
