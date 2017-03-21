package audio;

import java.util.Collection;

/**
 * Interface to store and control audio sources.
 * 
 * @author homelleon
 *
 */

public interface AudioManager {
	
	/**
	 * Adds list of audio sources into audio sources map array.
	 * 
	 * @param audioList
	 * 					  {@link Collection}<{@link AudioSource}> value of 
	 * 					  audio sources list
	 */
	void addAll(Collection<AudioSource> audioList);
	
	/**
	 * Adds one audio source into audio sources map array.
	 * 
	 * @param audio
	 * 				  {@link AudioSource} value
	 */
	void add(AudioSource audio);
	
	/**
	 * Returns audio source by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link AudioSource} value of chosen audio source
	 */
	AudioSource getByName(String name);
	
	/**
	 * Returns list of lights groupped by name.
	 * 
	 * @return {@link Collection}<{@link AudioSource}> value of audio sources
	 * 		   list
	 */
	Collection<AudioSource> getAll();	
	
	/**
	 * Returns audio master.
	 * 
	 * @return {@link AudioMaster} value
	 */
	AudioMaster getMaster();
	
	/**
	 * Clear all audio sources map and arrays.
	 */
	void clearAll();

}
