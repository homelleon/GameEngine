package voxels;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class Block {
	
	private final int step = ES.VOXEL_BLOCK_SIZE;
	private final float space = ES.VOXEL_SIZE;
	private final float size = space * step;
	
	
	private int texture;
	private List<Voxel> voxels;
	private Vector3f position;
	private boolean isRendered = true;
	
	public Block(Vector3f position) {
		this.position = position;
		this.voxels = new ArrayList<Voxel>();
		for(int x = -step / 2; x < step / 2; x++) {
			for(int y = -step / 2; y < step / 2; y++) {
				for(int z = -step / 2; z < step / 2; z++) {
					this.voxels.add(new Voxel(new Vector3f(position.x + space * x, 
							position.y + space * y, position.z + space * z), false));
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
