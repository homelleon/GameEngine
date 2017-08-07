package object.terrain.builder;

import core.settings.EngineSettings;
import object.terrain.terrain.ITerrain;
import object.terrain.terrain.ProceduredTerrain;
import object.texture.terrain.TerrainTexture;
import renderer.loader.Loader;

public class ProceduredTerrainBuilder extends TerrainBuilder implements ITerrainBuilder {

	@Override
	public ITerrain create(String name) {
		if(this.texturePack != null && this.blendTextureName != null) {
			TerrainTexture blendTexture = new TerrainTexture(this.blendTextureName, 
					Loader.getInstance().getTextureLoader().loadTexture(EngineSettings.TEXTURE_BLEND_MAP_PATH, this.blendTextureName));
			return new ProceduredTerrain(name, this.gridX, this.gridZ, this.texturePack, blendTexture, 
					this.amplitude, this.octaves, this.roughness);
		} else {
			throw new NullPointerException("Some parameters are missing in procedure terrain builder!");
		}
	}

}
