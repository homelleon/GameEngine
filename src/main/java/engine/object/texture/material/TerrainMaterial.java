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

	public void setTexturePack(TerrainTexturePack texturePack) {
		this.texturePack = texturePack;
	}

	public Texture2D getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(Texture2D heightMap) {
		this.heightMap = heightMap;
	}

	public Texture2D getNormalMap() {
		return normalMap;
	}

	public void setNormalMap(Texture2D normalMap) {
		this.normalMap = normalMap;
	}

	public Texture2D getBlendMap() {
		return blendMap;
	}

	public void setBlendMap(Texture2D blendMap) {
		this.blendMap = blendMap;
	}

	public String getName() {
		return name;
	}	

}
