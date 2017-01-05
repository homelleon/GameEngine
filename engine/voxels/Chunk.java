package voxels;

import scene.ES;

public class Chunk {
	
	private Block[][][] blocks = new Block[ES.VOXEL_CHUNK_SIZE + 1][ES.VOXEL_CHUNK_SIZE + 1][ES.VOXEL_CHUNK_SIZE + 1];
	
	private boolean isActive = false;
		
	public Chunk() {
		for(int x = 0; x <= ES.VOXEL_CHUNK_SIZE; x++) {
			for(int y = 0; y <= ES.VOXEL_CHUNK_SIZE; y++) {
				for(int z = 0; z <= ES.VOXEL_CHUNK_SIZE; z++) {
					blocks[x][y][z] = new Block();
				}
			}
		}
	}
	
	public boolean blockExist(int x, int y, int z) {
		boolean isExist = false;
		if(x >= 0 & y >= 0 & z >= 0 & x <= ES.VOXEL_CHUNK_SIZE & y <= ES.VOXEL_CHUNK_SIZE & z <= ES.VOXEL_CHUNK_SIZE) {
			isExist = true;
		}
		return isExist;
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
