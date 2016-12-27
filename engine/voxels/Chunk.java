package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class Chunk {
	
	private Block[][][] blocks = new Block[ES.VOXEL_CHUNK_SIZE][ES.VOXEL_CHUNK_SIZE][ES.VOXEL_CHUNK_SIZE];
	
	private boolean isActive = false;
		
	public Chunk() {
		for(int x = 0; x < ES.VOXEL_CHUNK_SIZE; x++) {
			for(int y = 0; y < ES.VOXEL_CHUNK_SIZE; y++) {
				for(int z = 0; z < ES.VOXEL_CHUNK_SIZE; z++) {
					blocks[x][y][z] = new Block();
				}
			}
		}
	}

	public Block getBlock(int x, int y, int z) {
		return blocks[x][y][z];
	}	

	public boolean getIsAcitve() {
		return this.isActive;
	}
	
	public void setIsActive(boolean value) {
		this.isActive = value;
	}

}
