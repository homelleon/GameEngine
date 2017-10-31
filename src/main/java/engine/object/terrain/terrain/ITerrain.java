package object.terrain.terrain;

import object.Nameable;
import object.camera.ICamera;
import object.terrain.generator.HeightsGenerator;
import object.texture.Texture2D;
import object.texture.terrain.TerrainTexturePack;
import primitive.model.Mesh;

/**
 * Terrain interface represents common landscape terrain methods.
 * 
 * @author homelleon
 * 
 * @see MappedTerrain
 * @see ProceduredTerrain
 */
public interface ITerrain extends Nameable {
	
	public static final int TERRAIN_VERTEX_COUNT = 256;
	public static final int TERRAIN_SIZE = 10000;
	
	public static final float TERRAIN_SCALE_XZ = 1f;
	public static final float TERRAIN_SCALE_Y = 1f;
	public static final int[] LOD_RANGE = new int[16];
	
	
	TerrainQuadTree getQuadTree();
	
	void updateQuadTree(ICamera camera);
	
	/**
	 * Gets size of terrain tiles.
	 * 
	 * @return {@link Float} value of terrain size
	 */
	float getSize();

	/**
	 * Gets x-coordinate at terrain sized coordinate axis.
	 * 
	 * @return {@link Float} value of x-coordinate
	 */
	float getX();

	/**
	 * Gets z-coordinate at terrain sized coordinate axis.
	 * 
	 * @return {@link Float} value of z-coordinate
	 */
	float getZ();
	
	void setXPosition(int xPosition);
	
	void setZPosition(int zPosition);

	/**
	 * Gets terrain raw model with all vertices.
	 * 
	 * @return {@link Mesh} value of terrain raw model
	 */
	Mesh getModel();

	/**
	 * Returns if terrain is visible.
	 * 
	 * @return true if terrain is visible<br>
	 *         false if terrain is hidden
	 */
	boolean isVisible();

	/**
	 * Sets terrain visible or hiding.
	 * 
	 * @param isVisible
	 *            {@link Boolean} value to define terrain visibity parameter
	 */
	void setVisible(boolean isVisible);

	/**
	 * Gets Terrain texture pack consisted from 4 texture.
	 * 
	 * @return {@link TerrainTexturePack} value of 4 texture pack
	 */
	TerrainTexturePack getTexturePack();

	/**
	 * Gets texture map of intensity of blending that effects on terrain
	 * lightning system.
	 * 
	 * @return {@link TerrainTexture} value of blending texture.
	 */
	Texture2D getBlendMap();

	/**
	 * Gets terrain height texture-map name.
	 * 
	 * @return {@link String} value of height texture map name
	 */
	String getHeightMapName();
	
	Texture2D getHeightMap();
	
	void setHeightMap(Texture2D heightMap);
	
	Texture2D getNormalMap();
	
	void setNormalMap(Texture2D normalMap);

	/**
	 * Returns if terrain is procedurally generated or not.
	 * 
	 * @return true if terrain is procedurally generated<br>
	 *         false if terrain is not procedurally generated
	 */
	boolean getIsProcedureGenerated();

	/**
	 * Gets maximum and minimum value (amplitude) of terrain height for
	 * procedure generator.
	 * 
	 * @return {link Float} value of terrain height amplitude
	 */
	float getAmplitude();

	/**
	 * Gets intensity of point to point changes due to terrain procedure
	 * generation.
	 * 
	 * @return {@link Integer} value of change intensity
	 */
	int getOctaves();

	/**
	 * Gets terrain edge roughness for terrain procedure generation.
	 * 
	 * @return {@link Float} value of terrain edge roughness
	 */
	float getRoughness();

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
	float getHeightOfTerrain(float worldX, float worldZ);
	
	/**
	 * Gets saved height generator.
	 * 
	 * @return {@link HeightsGenerator} object
	 */
	HeightsGenerator getGenerator();
	
	/**
	 * Clones current terrain.
	 * 
	 * @param name String value of cloned terrain name
	 * 
	 * @return {@link ITerrain} object
	 */
	ITerrain clone(String name);

}
