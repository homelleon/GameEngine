package manager.octree;

import java.util.stream.IntStream;

import tool.math.vector.Vector3f;
import tool.math.vector.Vector3i;

public class Voxel extends Node {
	
	private boolean isActive;
	private Vector3i location;
	private Vector3f position;
	
	public Voxel(int nodeSize, Vector3i location, Vector3f parentPosition) {
		super();
		IntStream.range(0, nodeSize).parallel();
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
