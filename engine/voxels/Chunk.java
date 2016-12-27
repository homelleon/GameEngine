package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class Chunk {
	
	private Block[][][] blocks;
	
	private boolean isActive = false;
		
	public Chunk() {
		for(int xB = 0; xB < ES.VOXEL_CHUNK_SIZE; xB++) {
			for(int yB = 0; yB < ES.VOXEL_CHUNK_SIZE; yB++) {
				for(int zB = 0; zB < ES.VOXEL_CHUNK_SIZE; zB++) {
					blocks[xB][yB][zB] = new Block();
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
