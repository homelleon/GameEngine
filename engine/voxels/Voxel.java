package voxels;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import scene.ES;

public class Voxel {

	private boolean isVisible;
	private boolean isAir = true;
	
	public Voxel() {}
	
	public Voxel(boolean isAir) {
		this.isAir = isAir;
	}
	
	public boolean getIsAir() {
		return this.isAir;
	}	
	
	public boolean getIsVisible() {
		return this.isVisible;
	}
	
	public void setAir(boolean value) {
		this.isAir = value;
	}
	
	public void setVisible(boolean value) {
		this.isVisible = value;
	}	

}
