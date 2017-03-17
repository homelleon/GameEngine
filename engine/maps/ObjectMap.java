package maps;

import java.util.List;

import entities.Entity;

/**
 * Map-interface of in-game default objects.
 * <p>Loads object map of entities to show in the game redactor.
 * 
 * @author homelleon
 * @version 1.0
 * 
 */
public interface ObjectMap {
	
	List<Entity> getALLEntities();
	Entity getEntity(int index);
	void loadEntity(String name, String model, String texName);
}
