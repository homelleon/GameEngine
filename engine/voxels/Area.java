package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class Area {
	
	private final int step = ES.VOXEL_AREA_SIZE;
	private final float space = ES.VOXEL_SIZE * ES.VOXEL_BLOCK_SIZE * ES.VOXEL_CHUNK_SIZE;
	private final float size = space * step;
	
	private List<Chunk> chunks;
	private Vector3f position;
	private boolean isRendered = true;
	
	public Area(Vector3f position) {
		this.position = position;
		this.chunks = new ArrayList<Chunk>();
		for(int x = -step / 2; x < step / 2; x++) {
			for(int y = -step / 2; y < step / 2; y++) {
				for(int z = -step / 2; z < step / 2; z++) {
					this.chunks.add(new Chunk(new Vector3f(position.x + space * x, 
							position.y + space * y, position.z + space * z)));
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
	
	public float getSize() {
		return size;
	}
	
	public void setRendered(boolean value) {
		this.isRendered = value;
	}

}
