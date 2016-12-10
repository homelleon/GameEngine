package voxels;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class Voxel {
	
	private Vector3f position;
	
	public Voxel(Vector3f position) { 
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	

}
