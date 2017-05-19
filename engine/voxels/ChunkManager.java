package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;
import toolbox.Maths;
import toolbox.Vector3i;

/**
 * Structured manage system is expected to implement chunks, blocks and voxels
 * contol sorting it in spacial arrays and provide access to it using friendly
 * interface.
 * <p>No matter that chunks are structured as one dimentional array for low
 * memory storage, user can get access to it using 3-dimentional 
 * {@link Vector3i} variable.   
 * 
 * @author homelleon
 * @version 1.0
 *
 */
public class ChunkManager implements ChunkManagerInterface {
	
	private List<Chunk> chunks = new ArrayList<Chunk>();
	private int size;
	private Vector3f position;
	
	public ChunkManager(int size, Vector3f position) {
		this.size = size;
		this.position = position;
		for(int x = 0; x <= size; x++) {
			for(int y = 0; y <= size; y++) {				
				for(int z = 0; z <= size; z++) {					
					this.chunks.add(new Chunk());
				}
			}
		}
	}
	
	@Override
	public void addChunk() {
		chunks.add(new Chunk());
	}
	
	@Override
	public void deleteChunk() {
		chunks.remove(chunks.size() - 1);
	}
	
	@Override
	public Vector3f getChunkPosition(Vector3i xyzIndex) {
		float step = ES.VOXEL_CHUNK_SIZE * ES.VOXEL_BLOCK_SIZE;
		return new Vector3f(position.x + xyzIndex.x * step, 
				position.y + xyzIndex.y * step, position.z + 
				xyzIndex.z * step);
	}
	
	@Override
	public Vector3f getChunkPosition(int index) {
		float step = ES.VOXEL_CHUNK_SIZE * ES.VOXEL_BLOCK_SIZE;
		int x = (int) Math.floor(index / Maths.sqr(size));
		int y = (int) Math.floor(index / size);
		int z = index;
		return new Vector3f(position.x + 
				Maths.tailOfDivisionNoReminder(x, size) * step, 
				position.y +  
				Maths.tailOfDivisionNoReminder(y, size)* step, 
				position.z +  
				Maths.tailOfDivisionNoReminder(z, size) * step);
	}
	
	@Override
	public Vector3f getBlockPosition(int index, Vector3i xyzIndex) {
		float step = ES.VOXEL_BLOCK_SIZE;
		Vector3f chunkPosition = getChunkPosition(index);
		return new Vector3f(chunkPosition.x + xyzIndex.x * step, 
				chunkPosition.y + xyzIndex.y * step, chunkPosition.z + 
				xyzIndex.z * step);
	}
	
	@Override
	public Vector3i getChunkXYZIndex(int index) {
		int x = (int) Math.floor(index / Maths.sqr(size));
		int y = (int) Math.floor(index / size);
		int z = index;
		return new Vector3i(Maths.tailOfDivisionNoReminder(x, size),
				Maths.tailOfDivisionNoReminder(y, size), 
				Maths.tailOfDivisionNoReminder(z, size));
	}
	
	@Override
	public boolean isChunkExist(int index) {
		boolean isExist = false;
		if(index >= 0 && index <this.chunks.size()) {
			isExist = true;
		}
		return isExist;
	}
	
	
	@Override
	public boolean isChunkExist(Vector3i xyzIndex) {
		boolean isExist = false;
		int index = xyzIndex.x * size * size + 
				xyzIndex.y * size + xyzIndex.z;
		if(index >= 0 && index < this.chunks.size()) {
			isExist = true;
		}
		return isExist;
	}
	
	@Override
	public Chunk getChunk(Vector3i position) {
		return chunks.get(position.x * size * size + 
				position.y * size + position.z); 
	} 
	
	@Override
	public Chunk getChunk(int index) {
		return chunks.get(index);
	}
	
	@Override
	public int getSize() {
		return chunks.size();
	}

	@Override
	public void clearAll() {
		chunks.clear();
	}
	
}
