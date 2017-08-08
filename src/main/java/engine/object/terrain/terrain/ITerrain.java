package object.terrain.terrain;

import object.Nameable;
import object.model.RawModel;
import object.texture.terrain.TerrainTexture;
import object.texture.terrain.TerrainTexturePack;

/**
 * Terrain interface represents common landscape terrain methods.
 * 
 * @author homelleon
 * 
 * @see MappedTerrain
 */
public interface ITerrain extends Nameable {

	/**
	 * Gets size of terrain tiles.
	 * 
	 * @return {@link Float} value of terrain size
	 */
	public float getSize();

	/**
	 * Gets x-coordinate at terrain sized coordinate axis.
	 * 
	 * @return {@link Float} value of x-coordinate
	 */
	public float getX();

	/**
	 * Gets z-coordinate at terrain sized coordinate axis.
	 * 
	 * @return {@link Float} value of z-coordinate
	 */
	public float getZ();
	
	public void setXPosition(int xPosition);
	
	public void setZPosition(int zPosition);

	/**
	 * Gets terrain raw model with all vertices.
	 * 
	 * @return {@link RawModel} value of terrain raw model
	 */
	public RawModel getModel();

	/**
	 * Returns if terrain is visible.
	 * 
	 * @return true if terrain is visible<br>
	 *         false if terrain is hidden
	 */
	public boolean isVisible();

	/**
	 * Sets terrain visible or hiding.
	 * 
	 * @param isVisible
	 *            {@link Boolean} value to define terrain visibity parameter
	 */
	public void setVisible(boolean isVisible);

	/**
	 * Gets Terrain texture pack consisted from 4 texture.
	 * 
	 * @return {@link TerrainTexturePack} value of 4 texture pack
	 */
	public TerrainTexturePack getTexturePack();

	/**
	 * Gets texture map of intensity of blending that effects on terrain
	 * lightning system.
	 * 
	 * @return {@link TerrainTexture} value of blending texture.
	 */
	public TerrainTexture getBlendMap();

	/**
	 * Gets terrain height texture-map name.
	 * 
	 * @return {@link String} value of height texture map name
	 */
	public String getHeightMapName();

	/**
	 * Returns if terrain is procedurally generated or not.
	 * 
	 * @return true if terrain is procedurally generated<br>
	 *         false if terrain is not procedurally generated
	 */
	public boolean isProcedureGenerated();

	/**
	 * Gets maximum and minimum value (amplitude) of terrain height for
	 * procedure generator.
	 * 
	 * @return {link Float} value of terrain height amplitude
	 */
	public float getAmplitude();

	/**
	 * Gets intensity of point to point changes due to terrain procedure
	 * generation.
	 * 
	 * @return {@link Integer} value of change intensity
	 */
	public int getOctaves();

	/**
	 * Gets terrain edge roughness for terrain procedure generation.
	 * 
	 * @return {@link Float} value of terrain edge roughness
	 */
	public float getRoughness();

	/**
	 * Gets terrain height value (y-coordinate in game world space) due to input
	 * x and z coodrinates in game world space.
	 * 
	 * @param worldX
	 *            {@link Float} value of x-coordinate in game world space
	 * @param worldZ
	 *            {@link Float} value of z-coordinate in game world space
	 * @return
	 */
	public float getHeightOfTerrain(float worldX, float worldZ);
	
	/**
	 * Clones current terrain.
	 * 
	 * @param name String value of cloned terrain name
	 * 
	 * @return {@link ITerrain} object
	 */
	public ITerrain clone(String name);

}
