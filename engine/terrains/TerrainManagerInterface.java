package terrains;

import java.util.Collection;

/**
 * Interface to store and control terrains.
 * 
 * @author homelleon
 *
 */
public interface TerrainManagerInterface {
	
	/**
	 * Adds list of terrains into terrains map array.
	 * 
	 * @param terrainList
	 * 					  {@link Collection}<{@link TerrainInterface}> value of terrain
	 * 					  list
	 */
	void addAll(Collection<TerrainInterface> terrainList);
	
	/**
	 * Adds one terrain into terrains map array.
	 * 
	 * @param terrain
	 * 				  {@link TerrainInterface} value
	 */
	void add(TerrainInterface terrain);
	
	/**
	 * Returns terrain by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link TerrainInterface} value of chosen terrain
	 */
	TerrainInterface getByName(String name);
	
	/**
	 * Returns list of terrains groupped by name.
	 * 
	 * @return {@link Collection}<{@link TerrainInterface}> value of terrains
	 * 		   list
	 */
	Collection<TerrainInterface> getAll();	
	
	/**
	 * Clear all terrains map and arrays.
	 */
	void clearAll();

}
