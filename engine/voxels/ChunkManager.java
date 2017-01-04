package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;
import toolbox.Maths;

public class ChunkManager {
	
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
	
	public void addChunk() {
		chunks.add(new Chunk());
	}
	
	public void deleteChunk() {
		chunks.remove(chunks.size() - 1);
	}
	
	public Vector3f getChunkPosition(int x, int y, int z) {
		float step = ES.VOXEL_CHUNK_SIZE * ES.VOXEL_BLOCK_SIZE;
		return new Vector3f(position.x + x * step, 
				position.y + y * step, position.z + z * step);
	}
	
	public Vector3f getChunkPosition(int index) {
		float step = ES.VOXEL_CHUNK_SIZE * ES.VOXEL_BLOCK_SIZE;
		int x = (int) Math.floor(index / Maths.sqr(size));
		int y = (int) Math.floor(index / size);
		int z = index;
		return new Vector3f(position.x + Maths.tailOfDivisionNoReminder(x, size) * step, 
				position.y +  Maths.tailOfDivisionNoReminder(y, size)* step, 
				position.z +  Maths.tailOfDivisionNoReminder(z, size) * step);
	}
	
	public Vector3f getBlockPosition(int index, int x, int y, int z) {
		float step = ES.VOXEL_BLOCK_SIZE;
		Vector3f chunkPosition = getChunkPosition(index);
		return new Vector3f(chunkPosition.x + x * step, 
				chunkPosition.y + y * step, chunkPosition.z + z * step);
	}
	
	public Chunk getChunk(int x, int y, int z) {
			return chunks.get(x * size * size + y * size + z); 
	}
	
	public Chunk getChunk(int index) {
		return chunks.get(index);
	}
	
	public int getSize() {
		return chunks.size();
	}
	
}
