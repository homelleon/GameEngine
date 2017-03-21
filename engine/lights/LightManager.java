package lights;

import java.util.Collection;

/**
 * Interface to store and control lights.
 * 
 * @author homelleon
 *
 */

public interface LightManager {
	
	/**
	 * Adds list of lights into lights map array.
	 * 
	 * @param lightList
	 * 					  {@link Collection}<{@link Light}> value of lights
	 * 					  list
	 */
	void addAll(Collection<Light> lightList);
	
	/**
	 * Adds one light into lights map array.
	 * 
	 * @param light
	 * 				  {@link Light} value
	 */
	void add(Light light);
	
	/**
	 * Returns light by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link Light} value of chosen light
	 */
	Light getByName(String name);
	
	/**
	 * Returns list of lights groupped by name.
	 * 
	 * @return {@link Collection}<{@link Light}> value of lights
	 * 		   list
	 */
	Collection<Light> getAll();	
	
	/**
	 * Clear all lights map and arrays.
	 */
	void clearAll();

}
