package object.voxel.manager;

import org.lwjgl.util.vector.Vector3f;

import object.voxel.Chunk;
import tool.Vector3i;

/**
 * Chunk Manager Interface for external chunks, blocks and voxel control.
 * 
 * @author homelleon
 *
 */
public interface IChunkManager {

	/**
	 * Adds one chunk ito the chunks array.
	 * <p>
	 * TODO: need to send values of including blocks.
	 */
	public void addChunk();

	/**
	 * Delete one chunk from chunks array.
	 * <p>
	 * TODO: need to choose chunk number.
	 */
	public void deleteChunk();

	/**
	 * Returns world coordinate position of chunk by its strucutred xyz index
	 * world position.
	 * 
	 * @param xyzIndex
	 *            {@link Vector3i} value of structured xyz index chunk world
	 *            position
	 * @return {@link Vector3f} value of chunk world coordinate position
	 */
	public Vector3f getChunkPosition(Vector3i xyzIndex);

	/**
	 * Returns chunk world position by its index position in array.
	 * 
	 * @param index
	 *            {@link Integer} value of index position in array
	 * @return {@link Vector3f} value of chunk position in world coordinate
	 *         space
	 */
	public Vector3f getChunkPosition(int index);

	/**
	 * Returns block world position accroding chunk position it depended on and
	 * using structured xyz index world position of block.
	 * 
	 * @param index
	 *            {@link Integer} value of chunk array index position
	 * @param xzyIndex
	 *            {@link Vector3i} value of block structured xyz position in
	 *            chunk
	 * @return {@link Vector3f} value of block position in world coordinate
	 *         space
	 */
	public Vector3f getBlockPosition(int index, Vector3i xzyIndex);

	/**
	 * Returns xyz index of chunk in structured chunk system by its index
	 * position in array.
	 * 
	 * @param index
	 *            {@link Integer} value of index position in array
	 * @return {@link Vector3i} value of strucured xyz index world position of
	 *         chunk
	 */
	public Vector3i getChunkXYZIndex(int index);

	/**
	 * Checks if the chunk chosen by index position in array is existed.
	 * 
	 * @param index
	 *            {@link Integer} value of index position in array
	 * @return true if chunk is existed<br>
	 *         false if chunks is not existed
	 */
	public boolean isChunkExist(int index);

	/**
	 * Checks if the chunk chosen by structured xyz index world position is
	 * existed.
	 * 
	 * @param xyzIndex
	 *            {@link Vector3i} value of structured xyz index world position
	 * @return true if chunk is existed<br>
	 *         false if chunks is not existed
	 */
	public boolean isChunkExist(Vector3i xyzIndex);

	/**
	 * Returns chunk by structured xyz index world position.
	 * 
	 * @param xyzIndex
	 *            {@link Vector3i} value of structured xyz index world position
	 * @return {@link Chunk} value
	 */
	public Chunk getChunk(Vector3i xyzIndex);

	/**
	 * Returns chunk by index position in chunks array.
	 * 
	 * @param index
	 *            {@link Integer} value chunk position in array
	 * @return {@link Chunk} value
	 */
	public Chunk getChunk(int index);

	/**
	 * Returns size of chunks array.
	 * 
	 * @return {@link Integer} value of array size
	 */
	public int getSize();

	/**
	 * Clear all chunks and voxels arrays.
	 */
	public void clearAll();

}
