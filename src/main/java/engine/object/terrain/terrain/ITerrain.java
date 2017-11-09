package object.terrain.terrain;

import object.Nameable;
import object.camera.ICamera;
import object.texture.material.TerrainMaterial;
import primitive.buffer.VBO;

/**
 * Terrain interface represents common landscape terrain methods.
 * 
 * @author homelleon
 * 
 * @see MappedTerrain
 * @see Terrain
 */
public interface ITerrain extends Nameable {
	
	public static final int TERRAIN_VERTEX_COUNT = 512;
	public static final int TERRAIN_SIZE = 6000;
	
	
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
	
	TerrainMaterial getMaterial();

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
	 * Clones current terrain.
	 * 
	 * @param name String value of cloned terrain name
	 * 
	 * @return {@link ITerrain} object
	 */
	ITerrain clone(String name);

}
