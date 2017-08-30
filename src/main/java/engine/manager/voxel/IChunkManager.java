package manager.voxel;

import org.lwjgl.util.vector.Vector3f;

import object.voxel.Chunk;
import tool.math.vector.Vector3i;

/**
 * Chunk Manager Interface for external chunks, blocks and voxel control.
 * 
 * @author homelleon
 * @version 1.0.1
 * @see ChunkManager
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
	 * Returns world coordinate position of chunk by its 3 dimentional index
	 * position in the chunk manager.
	 * 
	 * @param indexPosition
	 *            {@link Vector3i} value of index position in the chunk manager 
	 *            position
	 * @return {@link Vector3f} value of chunk world coordinate position
	 */
	public Vector3f getChunkPosition(Vector3i indexPosition);

	/**
	 * Returns chunk world position by its index in array.
	 * 
	 * @param index
	 *            {@link Integer} value of index in array
	 * @return {@link Vector3f} value of chunk position in world coordinate
	 *         space
	 */
	public Vector3f getChunkPosition(int index);

	/**
	 * Returns block world position by its 3 dimentional index position in the chunk.
	 * 
	 * @param index
	 *            {@link Integer} value of chunk array index
	 * @param indexPosition
	 *            {@link Vector3i} value of block 3d index position in chunk
	 * @return {@link Vector3f} value of block position in world coordinates
	 *         space
	 */
	public Vector3f getBlockPosition(int index, Vector3i indexPosition);

	/**
	 * Returns 3d index position of chunk in the chunk manager by its index
	 * in the chunk manager array.
	 * 
	 * @param index
	 *            {@link Integer} value of index in array
	 * @return {@link Vector3i} value of index position in the chunk manager
	 */
	public Vector3i getChunkIndexVector(int index);

	/**
	 * Checks if the chunk chosen by index in the chunk manager is existed.
	 * 
	 * @param index
	 *            {@link Integer} value of index in the chunk manager
	 * @return true if chunk is existed<br>
	 *         false if chunks is not existed
	 */
	public boolean isChunkExist(int index);

	/**
	 * Checks if the chunk is chosen by 3d index position in the chunk manager is
	 * existed.
	 * 
	 * @param indexPosition
	 *            {@link Vector3i} value of 3d index position in the chunk manager
	 * @return true if chunk is existed<br>
	 *         false if chunks is not existed
	 */
	public boolean isChunkExist(Vector3i indexPosition);

	/**
	 * Returns chunk by structured xyz index world position.
	 * 
	 * @param indexPosition
	 *            {@link Vector3i} value of 3d index position in the chunk manager
	 * @return {@link Chunk} value
	 */
	public Chunk getChunk(Vector3i indexPosition);

	/**
	 * Returns chunk by its index in the chunk manager.
	 * 
	 * @param index
	 *            {@link Integer} value chunk index in the chunk manager
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
