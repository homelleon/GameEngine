package object.map.levelMap;

import java.util.List;

import object.entity.entity.Entity;

/**
 * Map-interface of in-game default objects.
 * <p>
 * Loads object map of entities to show in the game redactor.
 * 
 * @author homelleon
 * @version 1.0
 * 
 */
public interface LevelMapInterface {

	List<Entity> getALLEntities();

	Entity getEntity(int index);

	void addEntity(Entity entity);
}
