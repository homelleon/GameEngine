package object.terrain.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.terrain.terrain.ITerrain;

/**
 * Terrain manager for controling and storing structured map and arrays of
 * terrains.
 * 
 * @author homelleon
 * @version 1.0
 */
public class TerrainManager implements ITerrainManager {

	Map<String, ITerrain> terrains = new HashMap<String, ITerrain>();

	@Override
	public void addAll(Collection<ITerrain> terrainList) {
		if ((terrainList != null) && (!terrainList.isEmpty())) {
			for (ITerrain terrain : terrainList) {
				this.terrains.put(terrain.getName(), terrain);
			}
		}
	}
	
	@Override
	public void addAll(List<ITerrain> terrainList) {
		if ((terrainList != null) && (!terrainList.isEmpty())) {
			for (ITerrain terrain : terrainList) {
				this.terrains.put(terrain.getName(), terrain);
			}
		} else {
			throw new NullPointerException("Trying to add null value into TerrainManager array!");
		}
	}

	@Override
	public void add(ITerrain terrain) {
		if (terrain != null) {
			this.terrains.put(terrain.getName(), terrain);
		} else {
			throw new NullPointerException("Trying to add null value into TerrainManager array!");
		}
	}

	@Override
	public ITerrain get(String name) {
		ITerrain terrain = null;
		if (this.terrains.containsKey(name)) {
			terrain = this.terrains.get(name);
		}
		return terrain;
	}

	@Override
	public Collection<ITerrain> getAll() {
		return this.terrains.values();
	}
	
	@Override
	public boolean delete(String name) {
		if(this.terrains.containsKey(name)) {
			this.terrains.remove(name);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void clean() {
		this.terrains.clear();
	}

}
