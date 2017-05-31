package objects.particles;

import java.util.Collection;

import objects.terrains.TerrainInterface;

/**
 * Interface to store and control paricle systems.
 * 
 * @author homelleon
 *
 */

public interface ParticleManagerInterface {
	
	/**
	 * Adds list of paricle systems into the paricle systems map array.
	 * 
	 * @param particleList
	 * 					  {@link Collection}<{@link ParticleSystem}> value of 
	 *                    paricle systems list
	 */
	void addAll(Collection<ParticleSystem> particleList);
	
	/**
	 * Adds one paricle system into the paricle systems map array.
	 * 
	 * @param particle
	 * 				  {@link ParticleSystem} value
	 */
	void add(ParticleSystem particle);
	
	/**
	 * Returns paricle system by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link ParticleSystem} value of chosen paricle system
	 */
	ParticleSystem getByName(String name);
	
	/**
	 * Returns list of paricle systems groupped by name.
	 * 
	 * @return {@link Collection}<{@link ParticleSystem}> value of paricle systems
	 * 		   list
	 */
	Collection<ParticleSystem> getAll();	
	
	/**
	 * Clear all paricle systems map and arrays.
	 */
	void clearAll();

}
