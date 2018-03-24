package primitive.texture.terrain;

import core.settings.EngineSettings;
import primitive.buffer.Loader;
import primitive.texture.Texture2D;

public class TerrainTexturePackBuilder {
	
	private String backgroundTextureName;
	private String rTextureName;
	private String gTextureName;
	private String bTextureName;

	public TerrainTexturePackBuilder setBackgroundTexture(String textureName) {
		this.backgroundTextureName = textureName;
		return this;
	}

	public TerrainTexturePackBuilder setRedTexture(String textureName) {
		this.rTextureName = textureName;
		return this;
	}
	
	public TerrainTexturePackBuilder setGreenTexture(String textureName) {
		this.gTextureName = textureName;
		return this;
	}

	public TerrainTexturePackBuilder setBlueTexture(String textureName) {
		this.bTextureName = textureName;
		return this;
	}

	public TerrainTexturePack create(String name) {
		return new TerrainTexturePack(getTexture(this.backgroundTextureName), getTexture(this.rTextureName),
				getTexture(this.gTextureName), getTexture(this.bTextureName));
	}
	
	private Texture2D getTexture(String name) {
		return Loader.getInstance().getTextureLoader()
				.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, name);
	}

}
