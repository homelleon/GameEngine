package objects.voxels;

import core.settings.ES;
import toolbox.Vector3i;

public class Chunk {
	
private Block[] blocks = new Block[(ES.VOXEL_CHUNK_SIZE + 1) * (ES.VOXEL_CHUNK_SIZE + 1) * (ES.VOXEL_CHUNK_SIZE + 1)];
	
	private boolean isActive = true;
		
	public Chunk() {
		for(int i = 0; i < ((ES.VOXEL_CHUNK_SIZE + 1) * (ES.VOXEL_CHUNK_SIZE + 1) * (ES.VOXEL_CHUNK_SIZE + 1)); i++) {
			blocks[i] = new Block();
		}
	}
	
	public boolean isBlockExist(int x, int y, int z) {
		boolean isExist = false;
		if(x >= 0 &&
				y >= 0 &&
				z >= 0 && 
				x <= ES.VOXEL_CHUNK_SIZE && 
				y <= ES.VOXEL_CHUNK_SIZE &&
				z <= ES.VOXEL_CHUNK_SIZE) {
			isExist = true;
		}
		return isExist;
	}
	
	public boolean isBlockExist(Vector3i position) {
		boolean isExist = false;
		if(position.x >= 0 && 
				position.y >= 0 && 
				position.z >= 0 &&
				position.x <= ES.VOXEL_CHUNK_SIZE && 
				position.y <= ES.VOXEL_CHUNK_SIZE &&
				position.z <= ES.VOXEL_CHUNK_SIZE) {
			isExist = true;
		}
		return isExist;
	}
	
	public Block getBlock(int x, int y, int z) {
		return blocks[x * ES.VOXEL_CHUNK_SIZE * ES.VOXEL_CHUNK_SIZE +
		              y * ES.VOXEL_CHUNK_SIZE + z];
	}	

	
	public Block getBlock(Vector3i position) {
		return blocks[position.x * ES.VOXEL_CHUNK_SIZE * ES.VOXEL_CHUNK_SIZE +
		              position.y * ES.VOXEL_CHUNK_SIZE + position.z];
	}

	public boolean getIsAcitve() {
		return this.isActive;
	}
	
	public void setIsActive(boolean value) {
		this.isActive = value;
	}

}
