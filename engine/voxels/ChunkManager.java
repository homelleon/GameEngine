package voxels;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {
	
	private List<Chunk> chunks = new ArrayList<Chunk>();
	private int size;
	
	ChunkManager(int size) {
		this.size = size;
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {				
				for(int z = 0; z < size; z++) {					
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
	
	
	public Chunk getChunk(int x, int y, int z) {
			return chunks.get(x * size * size + y * size + z); 
	}
	
}
