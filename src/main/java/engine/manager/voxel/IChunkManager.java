package manager.voxel;

import object.voxel.Block;
import object.voxel.Chunk;
import tool.math.vector.Vector3f;
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
	 * Returns chunk by structured xyz index world position.
	 * 
	 * @param indexPosition
	 *            {@link Vector3i} value of 3d index position in the chunk manager
	 * @return {@link Chunk} value
	 */
	Chunk getChunk(Vector3i indexPosition);

	/**
	 * Returns chunk by its index in the chunk manager.
	 * 
	 * @param chunkIndex
	 *            {@link Integer} value chunk index in the chunk manager
	 * @return {@link Chunk} value
	 */
	Chunk getChunk(int chunkIndex);
	
	/**
	 * Gets chunk by block index.
	 * 
	 * @param generalIndex int value of block index
	 * 
	 * @return {@link Chunk} object
	 */
	Chunk getChunkByGeneralIndex(int generalIndex);

	/**
	 * Returns world coordinate position of chunk by its 3 dimentional index
	 * position in the chunk manager.
	 * 
	 * @param indexPosition
	 *            {@link Vector3i} value of index position in the chunk manager 
	 *            position
	 * @return {@link Vector3f} value of chunk world coordinate position
	 */
	Vector3f getChunkPosition(Vector3i indexPosition);

	/**
	 * Returns chunk world position by its index in array.
	 * 
	 * @param chunkIndex
	 *            {@link Integer} value of index in array
	 * @return {@link Vector3f} value of chunk position in world coordinate
	 *         space
	 */
	Vector3f getChunkPositionByChunkIndex(int chunkIndex);
	
	/**
	 * Gets chunk position by block index.
	 * 
	 * @param generalIndex int value of block index
	 * @return {@link Vector3f} chunk position in 3 dimentions
	 */
	Vector3f getChunkPositionByGeneralIndex(int generalIndex);
	

	/**
	 * Returns 3d index position of chunk in the chunk manager by its index
	 * in the chunk manager array.
	 * 
	 * @param index
	 *            {@link Integer} value of index in array
	 * @return {@link Vector3i} value of index position in the chunk manager
	 */
	Vector3i getChunkIndexVector(int index);
	
	/**
	 * Gets block by block index.
	 * 
	 * @param generalIndex int value of block index
	 * @return {@link Block} object
	 */
	Block getBlockByGeneralIndex(int generalIndex);

	/**
	 * Returns block world position by its 3 dimentional index position in the chunk.
	 * 
	 * @param chunkIndex
	 *            {@link Integer} value of chunk array index
	 * @param indexPosition
	 *            {@link Vector3i} value of block 3d index position in chunk
	 * @return {@link Vector3f} value of block position in world coordinates
	 *         space
	 */
	Vector3f getBlockPosition(int chunkIndex, Vector3i indexPosition);
	
	/**
	 * Gets block position by chunk and block indexes.
	 * 
	 * @param chunkIndex int value of chunk index
	 * @param blockIndex int value of block index
	 * @return
	 */
	Vector3f getBlockPosition(int chunkIndex, int blockIndex);
	
	/**
	 * Gets block position by block index.
	 * 
	 * @param generalIndex int value of block index
	 * @return {@link Vector3f} position in 3 dimentions
	 */
	Vector3f getBlockPositionByGeneralIndex(int generalIndex);	
	
	/**
	 * Gets block 3 dimentional index within a chunk by general index value.
	 * 
	 * @param index value
	 * @return {@link Vector3i} index position in 3 dimentions within a chunk
	 */
	Vector3i getBlockIndexVector(int index);	


	/**
	 * Checks if the chunk chosen by index in the chunk manager is existed.
	 * 
	 * @param index
	 *            {@link Integer} value of index in the chunk manager
	 * @return true if chunk is existed<br>
	 *         false if chunks is not existed
	 */
	boolean isChunkExist(int index);

	/**
	 * Checks if the chunk is chosen by 3d index position in the chunk manager is
	 * existed.
	 * 
	 * @param indexPosition
	 *            {@link Vector3i} value of 3d index position in the chunk manager
	 * @return true if chunk is existed<br>
	 *         false if chunks is not existed
	 */
	boolean isChunkExist(Vector3i indexPosition);

	/**
	 * Returns size of chunks array.
	 * 
	 * @return {@link Integer} value of array size
	 */
	int getSize();

	/**
	 * Clear all chunks and voxels arrays.
	 */
	void clearAll();

}
