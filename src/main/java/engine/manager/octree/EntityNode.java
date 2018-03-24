package manager.octree;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import object.entity.Entity;
import tool.math.Maths;
import tool.math.vector.Vector3f;

/**
 * Octree node for entity.
 * 
 * @author homelleon
 *
 */
public class EntityNode extends Node<Vector3f> {
	
	public static final float RADIUS = 100f;
	public static final float BIG_RADIUS = (float) Math.sqrt(2 * RADIUS * RADIUS);
	private NavigableMap<Vector3f, String> positionedEntityMap = new TreeMap<Vector3f, String>();
	private Map<String, Entity> namedEntityMap = new HashMap<String, Entity>();

	public EntityNode(Vector3f position) {
		super("entityNode");
		this.position = position;
	}
	
	public synchronized void addEntity(Entity entity) {
		positionedEntityMap.put(entity.getPosition(), entity.getName());
		namedEntityMap.put(entity.getName(), entity);
		entity.setParentNode(this);
	}
	
	public NavigableMap<Vector3f, String> getPositionedEntities() {
		return positionedEntityMap;
	}
	
	public Map<String, Entity> getNamedEntities() {
		return namedEntityMap;
	}
	
	public boolean isBounding(Entity entity) {
		float distance = Maths.distance2Points(this.position, entity.getPosition());
		return distance <= BIG_RADIUS;
	}
	
	public boolean removeEntity(String name) {
		if(namedEntityMap.containsKey(name)) {
			Entity entity = namedEntityMap.get(name);
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
