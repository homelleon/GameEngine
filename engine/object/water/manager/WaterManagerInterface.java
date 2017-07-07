package object.water.manager;

import java.util.Collection;

import object.water.WaterTile;

/**
 * Water manager for controling and storing structured map and arrays of 
 * water tiles.
 * 
 * @author homelleon
 * @version 1.0
 */
public interface WaterManagerInterface {
	/**
	 * Adds list of water tiles into water tiles map array.
	 * 
	 * @param terrainList
	 * 					  {@link Collection}<{@link WaterTile}> value of water
	 * 					  tiles list
	 */
	void addAll(Collection<WaterTile> waterList);
	
	/**
	 * Adds one water tile into water tiles map array.
	 * 
	 * @param water
	 * 				  {@link WaterTile} value
	 */
	void add(WaterTile water);
	
	/**
	 * Returns water tile by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link WaterTile} value of chosen terrain
	 */
	WaterTile getByName(String name);
	
	/**
	 * Returns list of water tiles groupped by name.
	 * 
	 * @return {@link Collection}<{@link WaterTile}> value of water tiles
	 * 		   list
	 */
	Collection<WaterTile> getAll();	
	
	/**
	 * Clear all water tiles map and arrays.
	 */
	void clearAll();
}
