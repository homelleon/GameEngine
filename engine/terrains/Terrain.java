package terrains;

import models.RawModel;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public interface Terrain {
	
	public float getSize();	
	public float getX();
	public float getZ();

	public RawModel getModel();
	
	public String getName();
	
	public boolean isVisible();
	public void setVisible(boolean isVisible);

	public boolean isRendered();
	public void setRendered(boolean isRendered);

	public TerrainTexturePack getTexturePack();
	public TerrainTexture getBlendMap();	
	public String getHeightMapName();	
	public boolean isProcedureGenerated();
	
	public float getAmplitude();
	public int getOctaves();
	public float getRoughness();

	public float getHeightOfTerrain(float worldX, float worldZ);

}
