package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import scene.ES;

public class Chunk {
	
	private static final int BLOCK_SIZE = 2;
	private static final float SIZE = BLOCK_SIZE * ES.CHUNK_SIZE * ES.VOXEL_SIZE;
	

	private List<Block> blocks;
	private Vector3f position;
	private boolean isRendered = true;
	
	public Chunk(Vector3f position) {
		this.position = position;
		this.blocks = new ArrayList<Block>();
		for(int x = -ES.CHUNK_SIZE / 2; x < ES.CHUNK_SIZE / 2; x++) {
			for(int y = -ES.CHUNK_SIZE / 2; y < ES.CHUNK_SIZE / 2; y++) {
				for(int z = -ES.CHUNK_SIZE / 2; z < ES.CHUNK_SIZE / 2; z++) {
					this.blocks.add(new Block(new Vector3f(position.x + ES.VOXEL_SIZE * BLOCK_SIZE * x, 
							position.y + ES.VOXEL_SIZE * BLOCK_SIZE * y, position.z + ES.VOXEL_SIZE * BLOCK_SIZE * z), BLOCK_SIZE));
				}
			}
		}
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getSize() {
		return SIZE;
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
