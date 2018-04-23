package manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.EngineDebug;
import object.terrain.Terrain;

/**
 * Manages structured terrains.
 * 
 * @author homelleon
 * @version 1.0
 */
public class TerrainManager {

	Map<String, Terrain> terrains = new HashMap<String, Terrain>();

	public void addAll(Collection<Terrain> terrainList) {
		if (terrainList == null || terrainList.isEmpty()) {
			if (EngineDebug.hasDebugPermission())
				System.err.println("Trying to add null collection value into TerrainManager array!");
			return;
		}
		terrains.putAll(
				terrainList.stream()
					.collect(Collectors.toMap(
							Terrain::getName, Function.identity())));
	}

	public void add(Terrain terrain) {
		if (terrain == null) {
			if (EngineDebug.hasDebugPermission()) {
				System.err.println("Trying to add null value into TerrainManager array!");
			}
		}
		
		this.terrains.put(terrain.getName(), terrain);
	}

	public Terrain get(String name) {
		if (!terrains.containsKey(name)) {
			throw new NullPointerException("No terrain with name: " + name + " in the terrain array!");
		}
		return terrains.get(name);
	}

	public Collection<Terrain> getAll() {
		return terrains.values();
	}
	
	public boolean delete(String name) {
		if (!terrains.containsKey(name)) {
			return false;
		}
		terrains.remove(name);
		return true;
	}

	public void clean() {
		terrains.clear();
	}

}
