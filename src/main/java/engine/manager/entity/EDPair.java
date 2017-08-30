package manager.entity;

import object.entity.entity.IEntity;

/**
 * Pair of {@link IEntity} and float value of its distance from view frustum.
 * 
 * @author homelleon
 * @version 1.0
 */
public class EDPair {
	private float distance;
	private IEntity entity;
	
	public EDPair(IEntity entity, float distance) {
		this.entity = entity;
		this.distance = distance;
	}
	
	/**
	 * Gets distance.
	 * 
	 * @return float value of distance
	 */
	public float getDistance() {
		return this.distance;
	}
	
	/**
	 * Gets entity.
	 * 
	 * @return {@link IEntity} value of entity object
	 */
	public IEntity getEntity() {
		return this.entity;
	}
	
	/**
	 * Checks if entity is in camera frustum view.
	 * 
	 * @return <i>true</i> if entity is in frustum view<br>
	 * 		   <i>false</i> if entity is out of frustum view
	 */
	public boolean valid() {
		return distance > 0;
	}
}
