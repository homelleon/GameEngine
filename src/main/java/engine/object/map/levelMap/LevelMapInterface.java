package object.map.levelMap;

import java.util.List;

import object.entity.entity.Entity;
import object.terrain.terrain.Terrain;

/**
 * Contains lists of objects.
 * 
 * @author homelleon
 * @version 1.0
 * 
 * @see LevelMap
 */
public interface LevelMapInterface {

	List<Entity> getEntities();

	Entity getEntity(int index);

	void addEntity(Entity entity);
	
	List<Terrain> getTerrains();

	Terrain getTerrain(int index);

	void addTerrain(Terrain terrain);
}
