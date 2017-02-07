package entities;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cameras.Camera;

public interface EntityManager {
	
	void addAll(Collection<Entity> entityList);
	void addPointedList(Collection<Entity> pointedList);
	void addFrustumMap(Map<Float, List<Entity>> frustumMap);
	void add(Entity entity); 
	void addPointed(Entity entity);
	void addInFrustum(float distance, Entity entity);
	void clearPointed();
	void clearAll();
	Entity getByName(String name);
	Collection<Entity> getAll();
	Map<Float, List<Entity>> getFromFrustum();
	List<Entity> getPointed();
}
