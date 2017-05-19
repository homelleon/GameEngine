package terrains;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Terrain manager for controling and storing structured map and arrays of 
 * terrains.
 * 
 * @author homelleon
 * @version 1.0
 */
public class TerrainManager implements TerrainManagerInterface {
	
	Map<String, TerrainInterface> terrains = new HashMap<String, TerrainInterface>();

	@Override
	public void addAll(Collection<TerrainInterface> terrainList) {
		if((terrainList != null) && (!terrainList.isEmpty())) {
			for(TerrainInterface terrain : terrainList) {
				this.terrains.put(terrain.getName(), terrain);
			}
		}		
	}

	@Override
	public void add(TerrainInterface terrain) {
		if(terrain != null) {
			this.terrains.put(terrain.getName(), terrain); 		
		}
	}

	@Override
	public TerrainInterface getByName(String name) {
		TerrainInterface terrain = null;
		if(this.terrains.containsKey(name)) {
			terrain = this.terrains.get(name);
		}
		return terrain;
	}

	@Override
	public Collection<TerrainInterface> getAll() {
		return this.terrains.values();
	}
	
	@Override
	public void clearAll() {
		this.terrains.clear();
	}

}
