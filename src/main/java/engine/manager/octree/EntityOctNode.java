package manager.octree;

import tool.math.vector.Vector3f;

public class EntityOctNode extends Node {
	
	private Vector3f position;

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

}
