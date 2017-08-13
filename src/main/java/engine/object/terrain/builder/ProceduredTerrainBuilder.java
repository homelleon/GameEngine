package object.terrain.builder;

import object.terrain.terrain.ITerrain;
import object.terrain.terrain.ProceduredTerrain;

public class ProceduredTerrainBuilder extends TerrainBuilder implements ITerrainBuilder {

	@Override
	public ITerrain create(String name) {
		if(this.texturePack != null && this.blendTexture != null) {
			return new ProceduredTerrain(name, this.gridX, this.gridZ, this.texturePack, blendTexture, 
					this.amplitude, this.octaves, this.roughness);
		} else {
			throw new NullPointerException("Some parameters are missing in procedure terrain builder!");
		}
	}

}
