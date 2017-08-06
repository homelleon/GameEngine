package object.terrain.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import object.terrain.terrain.Terrain;

/**
 * Terrain manager for controling and storing structured map and arrays of
 * terrains.
 * 
 * @author homelleon
 * @version 1.0
 */
public class TerrainManager implements TerrainManagerInterface {

	Map<String, Terrain> terrains = new HashMap<String, Terrain>();

	@Override
	public void addAll(Collection<Terrain> terrainList) {
		if ((terrainList != null) && (!terrainList.isEmpty())) {
			for (Terrain terrain : terrainList) {
				this.terrains.put(terrain.getName(), terrain);
			}
		}
	}

	@Override
	public void add(Terrain terrain) {
		if (terrain != null) {
			this.terrains.put(terrain.getName(), terrain);
		}
	}

	@Override
	public Terrain getByName(String name) {
		Terrain terrain = null;
		if (this.terrains.containsKey(name)) {
			terrain = this.terrains.get(name);
		}
		return terrain;
	}

	@Override
	public Collection<Terrain> getAll() {
		return this.terrains.values();
	}

	@Override
	public void clearAll() {
		this.terrains.clear();
	}

}
