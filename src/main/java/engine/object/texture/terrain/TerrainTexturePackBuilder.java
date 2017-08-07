package object.texture.terrain;

import core.settings.EngineSettings;
import renderer.loader.Loader;

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
		return new TerrainTexturePack(getTerrainTexture(this.backgroundTextureName), getTerrainTexture(this.rTextureName),
				getTerrainTexture(this.gTextureName), getTerrainTexture(this.bTextureName));
	}
	
	private TerrainTexture getTerrainTexture(String name) {
		return new TerrainTexture(name, Loader.getInstance().getTextureLoader()
				.loadTexture(EngineSettings.TEXTURE_TERRAIN_PATH, name));
	}

}
