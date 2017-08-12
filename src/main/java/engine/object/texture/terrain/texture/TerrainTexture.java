package object.texture.terrain.texture;

import object.Nameable;

public class TerrainTexture implements Nameable {

	private int textureID;
	private String name;

	public TerrainTexture(String name, int textureID) {
		this.textureID = textureID;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getTextureID() {
		return textureID;
	}

}
