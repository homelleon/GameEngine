package object.map.levelMap;

import java.util.ArrayList;
import java.util.List;

import object.entity.entity.Entity;
import object.terrain.terrain.Terrain;

/**
 * Map that controls entity objects to load in the editor menu.
 * 
 * @author homelleon
 *
 */
public class LevelMap implements LevelMapInterface {

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Terrain> terrains = new ArrayList<Terrain>();

	@Override
	public List<Entity> getEntities() {
		return entities;
	}

	@Override
	public Entity getEntity(int index) {
		return entities.get(index);
	}

	@Override
	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	@Override
	public List<Terrain> getTerrains() {
		return terrains;
	}

	@Override
	public Terrain getTerrain(int index) {
		return terrains.get(index);
	}

	@Override
	public void addTerrain(Terrain terrain) {
		terrains.add(terrain);		
	}

}
