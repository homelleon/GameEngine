package manager.octree;

import tool.math.vector.Vector3f;

/**
 * Octree node for entity.
 * 
 * @author homelleon
 *
 */
public class EntityOctNode extends Node {
	
	private Vector3f position;

	/**
	 * Gets current node position in world space.
	 * 
	 * @return {@link Vector3f} 3-dimentional position
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * Sets node position in world space.
	 * 
	 * @param position {@link Vector3f} value in 3 dimentions
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}

}
