package manager.terrain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.debug.EngineDebug;
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
			terrains.putAll(
					terrainList.stream()
						.collect(Collectors.toMap(
								ITerrain::getName, Function.identity())));
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null collection value into TerrainManager array!");
			}
		}
	}

	@Override
	public void add(ITerrain terrain) {
		if (terrain != null) {
			this.terrains.put(terrain.getName(), terrain);
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null value into TerrainManager array!");
			}
		}
	}

	@Override
	public ITerrain get(String name) {
		if (this.terrains.containsKey(name)) {
			return this.terrains.get(name);
		} else {
			throw new NullPointerException(
					"No terrain with name: " + name + " in the terrain array!");
		}
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
