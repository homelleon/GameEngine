package manager.octree;

import tool.math.vector.Vector3f;
import tool.math.vector.Vector3i;

public class Voxel extends Node {
	
	private boolean isActive;
	private Vector3i location;
	private Vector3f position;
	private int currentVisualLevel;
	private int nodeSize;
	
	public Voxel(int nodeSize, Vector3i location, Vector3f parentPosition, int parentVisualLevel) {
		super();
		this.nodeSize = nodeSize;
		this.currentVisualLevel = parentVisualLevel - 1;
		if(currentVisualLevel > 0) {
			if(this.isLeaf()) {
				this.setLeaf(false);
			}
			for(int x = 0; x < nodeSize; x++) {
				for(int y = 0; y < nodeSize; y++) {
					for(int z = 0; z < nodeSize; z++) {
						this.addChild(new Voxel(8, new Vector3i(x,y,z), position, currentVisualLevel));
					}
				}
			}
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Vector3i getLocation() {
		return location;
	}

	public Vector3f getPosition() {
		return position;
	}

}
