package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class Chunk {
	
	private Block[][][] blocks;
	
	private int x;
	private int y;
	private int z;
	
	private boolean isActive = false;
		
	public Chunk(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		for(int xB = 0; xB < ES.VOXEL_CHUNK_SIZE; xB++) {
			for(int yB = 0; yB < ES.VOXEL_CHUNK_SIZE; yB++) {
				for(int zB = 0; zB < ES.VOXEL_CHUNK_SIZE; zB++) {
					blocks[xB][yB][zB] = new Block();
				}
			}
		}
	}
	
	
	
	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public int getZ() {
		return z;
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
