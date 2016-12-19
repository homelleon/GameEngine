package voxels;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class VoxelGrid {
	
	private String name;	
	private Voxel[][][] voxels;
	private Vector3f position;
	private int size;
	private int chunkSize = ES.VOXEL_CHUNK_SIZE;

	
	public VoxelGrid(Vector3f position, int size) {
		this.position = position;
		this.size = size;
		this.voxels = new Voxel[size][size][size];
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				for(int z = 0; z < size; z++) {
					voxels[x][y][z] = new Voxel();
				}
			}
		}
	}
	
	public Voxel getVoxel(int x, int y, int z) {
		return this.voxels[x][y][z];
	}
	
	public void setVoxel(int x, int y, int z, Voxel voxel) {
		this.voxels[x][y][z] = voxel;
	}
	
	public Voxel[][][] getChunk(int x, int y, int z) {
		Voxel[][][] chunk = new Voxel[chunkSize][chunkSize][chunkSize];
		int stepX = size / chunkSize;
		int stepY = size / chunkSize;
		int stepZ = size / chunkSize;
		for(int i = 0; i<chunkSize; i++) {
			for(int j = 0; j<chunkSize; j++) {
				for(int k = 0; i<chunkSize; k++) {
					chunk[i][j][k] = this.voxels[stepX * x + i][stepY * y + j][stepZ * z + k];
				}
			}
		}
		return chunk; 
	}
	
	public String getName() {
		return this.name;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public int getSize() {
		return size;
	}

}
