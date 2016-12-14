package voxels;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;

public class Voxel {
	
	private Vector3f position;
	private boolean isRendered = true;
	private boolean isAir;
	
	public Voxel(Vector3f position, boolean isAir) { 
		this.position = position;
		this.isAir = isAir;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public boolean getIsAir() {
		return this.isAir;
	}	
	
	public boolean isRendered() {
		return this.isRendered;
	}
	

}
