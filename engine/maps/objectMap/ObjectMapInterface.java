package maps.objectMap;

import java.util.List;

import objects.entities.EntityInterface;

/**
 * Map-interface of in-game default objects.
 * <p>Loads object map of entities to show in the game redactor.
 * 
 * @author homelleon
 * @version 1.0
 * 
 */
public interface ObjectMapInterface {
	
	List<EntityInterface> getALLEntities();
	EntityInterface getEntity(int index);
	void loadEntity(String name, String model, String texName);
}
