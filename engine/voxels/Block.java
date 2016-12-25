package voxels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import scene.ES;

public class Block {

	
	private boolean isActive = false;
	
	public Block() {}	

	public boolean getIsActive() {
		return this.isActive;
	}
	
	public void setIsActive(boolean value) {
		this.isActive = value;
	}

}
