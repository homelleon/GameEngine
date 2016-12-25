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
					this.chunks.add(new Chunk(x,y,z));
				}
			}
		}
	}
	
	public Chunk getChunk(int x, int y, int z) {
		Boolean found = false; 
		Chunk chunk = null;
		for(int i = 0; i < chunks.size(); i++) {
			chunk = chunks.get(i);
			if(chunk.getX() == x && chunk.getY() == y && chunk.getZ() == z) {
				found = true;
				break;
			}
		}
		if(found == false) {
			System.err.println("Wrong chunk position! " + 
					String.valueOf(x) + ";" + String.valueOf(y) + 
					";" + String.valueOf(z));
		}
		return chunk;
	}
	
	public Chunk getChunk(List<Chunk> chunkArray, int x, int y, int z) {
		Boolean found = false; 
		Chunk chunk = null;
		for(int i = 0; i < chunkArray.size(); i++) {
			chunk = chunkArray.get(i);
			if(chunk.getX() == x && chunk.getY() == y && chunk.getZ() == z) {
				found = true;
				break;
			}
		}
		if(found == false) {
			System.err.println("Wrong chunk position! " + 
					String.valueOf(x) + ";" + String.valueOf(y) + 
					";" + String.valueOf(z));
		}
		return chunk;
	}
	
}
