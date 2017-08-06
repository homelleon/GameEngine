package object.terrain.manager;

import java.util.Collection;

import object.terrain.terrain.Terrain;

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
	 *            {@link Collection}<{@link Terrain}> value of terrain
	 *            list
	 */
	void addAll(Collection<Terrain> terrainList);

	/**
	 * Adds one terrain into terrains map array.
	 * 
	 * @param terrain
	 *            {@link Terrain} value
	 */
	void add(Terrain terrain);

	/**
	 * Returns terrain by name.
	 * 
	 * @param name
	 *            {@link String} value
	 * 
	 * @return {@link Terrain} value of chosen terrain
	 */
	Terrain getByName(String name);

	/**
	 * Returns list of terrains groupped by name.
	 * 
	 * @return {@link Collection}<{@link Terrain}> value of terrains
	 *         list
	 */
	Collection<Terrain> getAll();

	/**
	 * Clear all terrains map and arrays.
	 */
	void clearAll();

}
