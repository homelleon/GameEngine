package manager.octree;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import object.entity.entity.IEntity;
import tool.math.Maths;
import tool.math.vector.Vector3f;

/**
 * Octree node for entity.
 * 
 * @author homelleon
 *
 */
public class EntityNode extends Node {
	
	public static final float RADIUS = 100f;
	public static final float BIG_RADIUS = (float) Math.sqrt(2 * RADIUS * RADIUS);
	private Vector3f position = new Vector3f(0,0,0);
	private NavigableMap<Vector3f, String> positionedEntityMap = new TreeMap<Vector3f, String>();
	private Map<String, IEntity> namedEntityMap = new HashMap<String, IEntity>();

	public EntityNode(Vector3f position) {
		super();
		this.position = position;
	}
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
	
	public synchronized void addEntity(IEntity entity) {
		positionedEntityMap.put(entity.getPosition(), entity.getName());
		namedEntityMap.put(entity.getName(), entity);
		entity.setParentNode(this);
	}
	
	public NavigableMap<Vector3f, String> getPositionedEntities() {
		return positionedEntityMap;
	}
	
	public Map<String, IEntity> getNamedEntities() {
		return namedEntityMap;
	}
	
	public boolean isBounding(IEntity entity) {
		float distance = Maths.distance2Points(this.position, entity.getPosition());
		return distance <= BIG_RADIUS;
	}
	
	public boolean removeEntity(String name) {
		if(namedEntityMap.containsKey(name)) {
			IEntity entity = namedEntityMap.get(name);
			if(positionedEntityMap.containsKey(entity.getPosition())) {
				positionedEntityMap.remove(entity.getPosition());
				namedEntityMap.remove(name);
				entity.removeParentNode();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void clean() {
		positionedEntityMap.clear();
		namedEntityMap.clear();
	}

}
