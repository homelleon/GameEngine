package object.texture.terrain;

public class TerrainTexture {
	
	private int textureID;
	private String name;
	
	public TerrainTexture(String name, int textureID) {
		this.textureID = textureID;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getTextureID() {
		return textureID;
	}

	
}
