package entities;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface EntityManager {
	
	void addEntities(Collection<Entity> entityList);
	void addEntity(Entity entity);
	void clearAllEntities();
	Entity getEntity(String name);
	Collection getAllEntities();
	Map<Float, Entity> getFrustumEntities();
	List<Entity> getPointedEntities();

}
