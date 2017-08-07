package object.terrain.builder;

import core.settings.EngineSettings;
import object.terrain.terrain.ITerrain;
import object.terrain.terrain.MappedTerrain;
import object.texture.terrain.TerrainTexture;
import renderer.loader.Loader;

public class MappedTerrainBuilder extends TerrainBuilder implements ITerrainBuilder {

	@Override
	public ITerrain create(String name) {
		if(this.texturePack != null && this.blendTextureName != null && this.heightTextureName != null) {
			TerrainTexture blendTexture = new TerrainTexture(this.blendTextureName, 
					Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_BLEND_MAP_PATH, this.blendTextureName));
			return new MappedTerrain(name, this.gridX, this.gridZ, this.texturePack, blendTexture, this.heightTextureName);
		} else {
			throw new NullPointerException("Some parameters are missing in mapped terrain builder!");
		}
	}

}
