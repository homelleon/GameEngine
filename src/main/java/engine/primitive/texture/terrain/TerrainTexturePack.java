package primitive.texture.terrain;

import primitive.texture.Texture2D;

public class TerrainTexturePack {

	private String name;
	private Texture2D backgroundTexture;
	private Texture2D rTexture;
	private Texture2D gTexture;
	private Texture2D bTexture;	

	public TerrainTexturePack(Texture2D backgroundTexture, Texture2D rTexture, Texture2D gTexture,
			Texture2D bTexture) {
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
		this.name = "NoName";
	}

	public TerrainTexturePack(String name, Texture2D backgroundTexture, Texture2D rTexture,
			Texture2D gTexture, Texture2D bTexture) {
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Texture2D getBackgroundTexture() {
		return backgroundTexture;
	}

	public Texture2D getRTexture() {
		return rTexture;
	}

	public Texture2D getGTexture() {
		return gTexture;
	}

	public Texture2D getBTexture() {
		return bTexture;
	}

}
