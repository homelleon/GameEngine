package object.voxel;

import core.settings.EngineSettings;
import tool.math.Maths;
import tool.math.vector.Vector3i;

public class Chunk {

	private Block[] blocks = new Block[(EngineSettings.VOXEL_CHUNK_SIZE + 1) * (EngineSettings.VOXEL_CHUNK_SIZE + 1)
			* (EngineSettings.VOXEL_CHUNK_SIZE + 1)];

	private boolean isActive = true;

	public Chunk() {
		for (int i = 0; i < ((EngineSettings.VOXEL_CHUNK_SIZE + 1) * (EngineSettings.VOXEL_CHUNK_SIZE + 1)
				* (EngineSettings.VOXEL_CHUNK_SIZE + 1)); i++) {
			blocks[i] = new Block();
		}
	}

	public boolean isBlockExist(int x, int y, int z) {
		return (x >= 0 && y >= 0 && z >= 0 && x <= EngineSettings.VOXEL_CHUNK_SIZE && y <= EngineSettings.VOXEL_CHUNK_SIZE
				&& z <= EngineSettings.VOXEL_CHUNK_SIZE);
	}

	public boolean isBlockExist(Vector3i position) {
		return (position.x >= 0 && position.y >= 0 && position.z >= 0 && position.x <= EngineSettings.VOXEL_CHUNK_SIZE
				&& position.y <= EngineSettings.VOXEL_CHUNK_SIZE && position.z <= EngineSettings.VOXEL_CHUNK_SIZE);
	}

	public Block getBlock(int x, int y, int z) {
		return blocks[x * EngineSettings.VOXEL_CHUNK_SIZE * EngineSettings.VOXEL_CHUNK_SIZE
				+ y * EngineSettings.VOXEL_CHUNK_SIZE + z];
	}
	
	public Block getBlock(int index) {
		int x = (int) Math.floor(index / Maths.sqr(EngineSettings.VOXEL_CHUNK_SIZE));
		int y = (int) Math.floor(index / EngineSettings.VOXEL_CHUNK_SIZE);
		int z = index;
		x = Maths.tailOfDivisionNoReminder(x, EngineSettings.VOXEL_CHUNK_SIZE);
		y = Maths.tailOfDivisionNoReminder(y, EngineSettings.VOXEL_CHUNK_SIZE);
		z = Maths.tailOfDivisionNoReminder(z, EngineSettings.VOXEL_CHUNK_SIZE);
		return getBlock(x,y,z);		
	}

	public Block getBlock(Vector3i position) {
		return blocks[position.x * EngineSettings.VOXEL_CHUNK_SIZE * EngineSettings.VOXEL_CHUNK_SIZE
				+ position.y * EngineSettings.VOXEL_CHUNK_SIZE + position.z];
	}
	
	public Block[] getBlocks() {
		return this.blocks;
	}

	public boolean getIsAcitve() {
		return this.isActive;
	}

	public void setIsActive(boolean value) {
		this.isActive = value;
	}

}
