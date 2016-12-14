package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class Block {
	
	private int texture;
	private List<Voxel> voxels;
	private Vector3f position;
	private int size;
	private boolean isRendered = true;
	
	public Block(Vector3f position, int size) {
		this.position = position;
		this.size = size;
		this.voxels = new ArrayList<Voxel>();
		for(int x = -size / 2; x < size / 2; x++) {
			for(int y = -size / 2; y < size / 2; y++) {
				for(int z = -size / 2; z < size / 2; z++) {
					this.voxels.add(new Voxel(new Vector3f(position.x + ES.VOXEL_SIZE * x, 
							position.y + ES.VOXEL_SIZE * y, position.z + ES.VOXEL_SIZE * z), false));
				}
			}
		}
	}
	
	public List<Voxel> getVoxels() {
		return this.voxels;
	}
	
	public float getSize() {
		return this.size;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public boolean isRendered() {
		return isRendered;
	}

}
