package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import scene.ES;

public class Chunk {
	
	private static final float SIZE = ES.VOXEL_SIZE * ES.CHUNK_SIZE;
	
	private int texture;	
	private List<Voxel> voxels;
	private Vector3f position;
	
	public Chunk(RawModel voxelModel, Vector3f position, float ChunkSize) {
		this.position = position;
		this.voxels = new ArrayList<Voxel>();
		for(int x = -ES.CHUNK_SIZE / 2; x < ES.CHUNK_SIZE / 2; x++) {
			for(int y = -ES.CHUNK_SIZE / 2; y < ES.CHUNK_SIZE / 2; y++) {
				for(int z = -ES.CHUNK_SIZE / 2; z < ES.CHUNK_SIZE / 2; z++) {
					this.voxels.add(new Voxel(new Vector3f(position.x + ES.VOXEL_SIZE * x, 
							position.y + ES.VOXEL_SIZE * y, position.z + ES.VOXEL_SIZE * z)));
				}
			}
		}
	}
	
	public float getSize() {
		return SIZE;
	}
	
	public Voxel getVoxel(int id) {
		return this.voxels.get(id);
	}


}
