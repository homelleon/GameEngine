package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import scene.ES;

public class Chunk {
	
	private final int step = ES.VOXEL_CHUNK_SIZE;
	private final float space = ES.VOXEL_SIZE * ES.VOXEL_BLOCK_SIZE;
	private final float size = space * step;
	

	private List<Block> blocks;
	private Vector3f position;
	private boolean isRendered = true;
	
	public Chunk(Vector3f position) {
		this.position = position;
		this.blocks = new ArrayList<Block>();
		for(int x = -step / 2; x < step / 2; x++) {
			for(int y = -step / 2; y < step / 2; y++) {
				for(int z = -step / 2; z < step / 2; z++) {
					this.blocks.add(new Block(new Vector3f(position.x + space * x, 
							position.y + space * y, position.z + space * z)));
				}
			}
		}
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getSize() {
		return this.size;
	}
	
	public List<Block> getBlocks() {
		return this.blocks;
	}
	
	public void setRendered(boolean value) {
		this.isRendered = value;
	}
	
	public boolean isRendered() {
		return this.isRendered;
	}


}
