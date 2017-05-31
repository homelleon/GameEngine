package objects.terrains;

import objects.models.RawModel;
import objects.textures.TerrainTexture;
import objects.textures.TerrainTexturePack;

public interface TerrainInterface {
	
	public float getSize();	
	public float getX();
	public float getZ();

	public RawModel getModel();
	
	public String getName();
	
	public boolean isVisible();
	public void setVisible(boolean isVisible);

	public TerrainTexturePack getTexturePack();
	public TerrainTexture getBlendMap();	
	public String getHeightMapName();	
	public boolean isProcedureGenerated();
	
	public float getAmplitude();
	public int getOctaves();
	public float getRoughness();

	public float getHeightOfTerrain(float worldX, float worldZ);

}
