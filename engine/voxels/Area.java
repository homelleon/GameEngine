package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class Area {
	
	private static final float SIZE = ES.VOXEL_SIZE * ES.CHUNK_SIZE * ES.AREA_SIZE;
	
	private List<Chunk> chunks;
	private Vector3f position;
	private boolean isRendered = true;
	
	public Area(Vector3f position) {
		this.position = position;
		this.chunks = new ArrayList<Chunk>();
		for(int x = -ES.AREA_SIZE / 2; x < ES.AREA_SIZE / 2; x++) {
			for(int y = -ES.AREA_SIZE / 2; y < ES.AREA_SIZE / 2; y++) {
				for(int z = -ES.AREA_SIZE / 2; z < ES.AREA_SIZE / 2; z++) {
					this.chunks.add(new Chunk(new Vector3f(position.x + ES.CHUNK_SIZE * ES.VOXEL_SIZE * x, 
							position.y + ES.CHUNK_SIZE * ES.VOXEL_SIZE * y, position.z + ES.CHUNK_SIZE * ES.VOXEL_SIZE * z)));
				}
			}
		}			
	}
	
	public List<Chunk> getChunks() {
		return this.chunks;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public boolean isRendered() {
		return this.isRendered;
	}
	
	public void setRendered(boolean value) {
		this.isRendered = value;
	}

}
