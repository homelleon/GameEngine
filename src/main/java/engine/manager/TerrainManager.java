package manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.debug.EngineDebug;
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
		if ((terrainList != null) && (!terrainList.isEmpty())) {
			terrains.putAll(
					terrainList.stream()
						.collect(Collectors.toMap(
								Terrain::getName, Function.identity())));
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null collection value into TerrainManager array!");
			}
		}
	}

	public void add(Terrain terrain) {
		if (terrain != null) {
			this.terrains.put(terrain.getName(), terrain);
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null value into TerrainManager array!");
			}
		}
	}

	public Terrain get(String name) {
		if (this.terrains.containsKey(name)) {
			return this.terrains.get(name);
		} else {
			throw new NullPointerException(
					"No terrain with name: " + name + " in the terrain array!");
		}
	}

	public Collection<Terrain> getAll() {
		return this.terrains.values();
	}
	
	public boolean delete(String name) {
		if (!this.terrains.containsKey(name)) {
			return false;
		}
		this.terrains.remove(name);
		return true;
	}

	public void clean() {
		this.terrains.clear();
	}

}
