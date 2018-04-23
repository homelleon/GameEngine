package manager.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import manager.octree.Node;
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
	public static final float BIG_RADIUS = RADIUS * (float) Math.sqrt(2);
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
		return (distance > BIG_RADIUS) ? false : isInCell(entity);
	}
	
	private boolean isInCell(Entity entity) {
		Vector3f pointMin = getCellPointOffset(-RADIUS, -RADIUS, -RADIUS);
		Vector3f pointMax = getCellPointOffset(RADIUS, RADIUS, RADIUS);
		
		return Maths.pointIsInCube(entity.getPosition(), pointMin, pointMax);
	}
	
	private Vector3f getCellPointOffset(float offsetX, float offsetY, float offsetZ) {
		return new Vector3f(
				this.position.x + offsetX,
				this.position.y + offsetY,
				this.position.z + offsetZ);
	}
	
	public boolean removeEntity(String name) {
		if (!namedEntityMap.containsKey(name)) return false;
		
		Entity entity = namedEntityMap.get(name);
		if (!positionedEntityMap.containsKey(entity.getPosition())) return false;
		
		positionedEntityMap.remove(entity.getPosition());
		namedEntityMap.remove(name);
		entity.removeParentNode();
		return true;
	}
	
	public void clean() {
		positionedEntityMap.clear();
		namedEntityMap.clear();
	}

}
