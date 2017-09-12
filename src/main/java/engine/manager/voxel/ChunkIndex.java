package manager.voxel;

import object.voxel.data.FaceCullingData;

/**
 * Index data for chunk creation.
 * 
 * @author homelleon
 *
 */
public final class ChunkIndex {
	
	private volatile int i;
	private volatile int x;
	private volatile int y;
	private volatile int z;
	private volatile FaceCullingData fcd;
	
	public ChunkIndex(int i) {
		this.i = i;
	}
	
	/**
	 * Gets index.
	 * 
	 * @return int value of index
	 */
	public int getI() {
		return i;
	}
	
	/**
	 * Sets x position index.
	 * @param x int value of position index
	 * @return {@link ChunkIndex}
	 */
	public ChunkIndex setX(int x) {
		this.x = x;
		return this;
	}
	
	/**
	 * Gets x position index.
	 * 
	 * @return int value of x position index
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Sets y position index.
	 * @param y int value of position index
	 * @return {@link ChunkIndex}
	 */
	public ChunkIndex setY(int y) {
		this.y = y;
		return this;
	}
	
	/**
	 * Gets y position index.
	 * 
	 * @return int value of y position index
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Sets z position index.
	 * @param z int value of position index
	 * @return {@link ChunkIndex}
	 */
	public ChunkIndex setZ(int z) {
		this.z = z;
		return this;
	}
	
	/**
	 * Gets z position index.
	 * 
	 * @return int value of z position index
	 */
	public int getZ() {
		return z;
	}
	
	/**
	 * Sets cube face culling data for chunk index data.
	 *  
	 * @param fcd {@link FaceCullingData} value
	 * @return {@link ChunkIndex}
	 */
	public ChunkIndex setFCD(FaceCullingData fcd) {
		this.fcd = fcd;
		return this;
	}
	
	/**
	 * Gets cube face culling data from chunk index data.
	 * 
	 * @return {@link FaceCullingData} cube face culling data
	 */
	public FaceCullingData getFCD() {
		return this.fcd;
	}
}
