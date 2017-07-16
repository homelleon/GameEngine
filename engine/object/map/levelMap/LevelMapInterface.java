package object.map.levelMap;

import java.util.List;

import object.entity.entity.EntityInterface;

/**
 * Map-interface of in-game default objects.
 * <p>Loads object map of entities to show in the game redactor.
 * 
 * @author homelleon
 * @version 1.0
 * 
 */
public interface LevelMapInterface {
	
	List<EntityInterface> getALLEntities();
	EntityInterface getEntity(int index);
	void loadEntity(String name, String model, String texName);
}
