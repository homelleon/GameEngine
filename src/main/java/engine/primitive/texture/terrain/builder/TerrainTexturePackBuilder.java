package primitive.texture.terrain.builder;

import core.settings.EngineSettings;
import primitive.buffer.Loader;
import primitive.texture.Texture2D;
import primitive.texture.terrain.TerrainTexturePack;

public class TerrainTexturePackBuilder implements ITerrainTexturePackBuilder {
	
	private String backgroundTextureName;
	private String rTextureName;
	private String gTextureName;
	private String bTextureName;

	@Override
	public ITerrainTexturePackBuilder setBackgroundTexture(String textureName) {
		this.backgroundTextureName = textureName;
		return this;
	}

	@Override
	public ITerrainTexturePackBuilder setRedTexture(String textureName) {
		this.rTextureName = textureName;
		return this;
	}

	@Override
	public ITerrainTexturePackBuilder setGreenTexture(String textureName) {
		this.gTextureName = textureName;
		return this;
	}

	@Override
	public ITerrainTexturePackBuilder setBlueTexture(String textureName) {
		this.bTextureName = textureName;
		return this;
	}

	@Override
	public TerrainTexturePack create(String name) {
		return new TerrainTexturePack(getTexture(this.backgroundTextureName), getTexture(this.rTextureName),
				getTexture(this.gTextureName), getTexture(this.bTextureName));
	}
	
	private Texture2D getTexture(String name) {
		return Loader.getInstance().getTextureLoader()
				.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, name);
	}

}
