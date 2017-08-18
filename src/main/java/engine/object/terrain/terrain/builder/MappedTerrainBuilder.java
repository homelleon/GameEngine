package object.terrain.terrain.builder;

import core.settings.EngineSettings;
import object.terrain.terrain.ITerrain;
import object.terrain.terrain.MappedTerrain;
import object.texture.terrain.texture.TerrainTexture;
import renderer.loader.Loader;

public class MappedTerrainBuilder extends TerrainBuilder implements ITerrainBuilder {

	@Override
	public ITerrain create(String name) {
		if(this.texturePack != null && this.blendTexture != null && this.heightTextureName != null) {
			return new MappedTerrain(name, this.gridX, this.gridZ, this.texturePack, blendTexture, this.heightTextureName);
		} else {
			throw new NullPointerException("Some parameters are missing in mapped terrain builder!");
		}
	}

}
