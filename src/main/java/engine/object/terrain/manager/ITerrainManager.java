package object.terrain.manager;

import java.util.Collection;

import object.terrain.terrain.ITerrain;

/**
 * Interface to store and control terrains.
 * 
 * @author homelleon
 * @see TerrainManager
 *
 */
public interface ITerrainManager {

	/**
	 * Adds list of terrains into terrains map array.
	 * 
	 * @param terrainList
	 *            {@link Collection}<{@link ITerrain}> value of terrain
	 *            list
	 */
	void addAll(Collection<ITerrain> terrainList);

	/**
	 * Adds one terrain into terrains map array.
	 * 
	 * @param terrain
	 *            {@link ITerrain} value
	 */
	void add(ITerrain terrain);

	/**
	 * Returns terrain by name.
	 * 
	 * @param name
	 *            {@link String} value
	 * 
	 * @return {@link ITerrain} value of chosen terrain
	 */
	ITerrain get(String name);

	/**
	 * Returns list of terrains groupped by name.
	 * 
	 * @return {@link Collection}<{@link ITerrain}> value of terrains
	 *         list
	 */
	Collection<ITerrain> getAll();

	/**
	 * Clear all terrains map and arrays.
	 */
	void clean();

}
