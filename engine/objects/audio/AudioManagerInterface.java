package objects.audio;

import java.util.Collection;

/**
 * Interface to store and control audio sources.
 * 
 * @author homelleon
 *
 */

public interface AudioManagerInterface {
	
	/**
	 * Adds list of audio sources into audio sources map array.
	 * 
	 * @param audioList
	 * 					  {@link Collection}<{@link AudioSourceInterface}> value of 
	 * 					  audio sources list
	 */
	void addAll(Collection<AudioSourceInterface> audioList);
	
	/**
	 * Adds one audio source into audio sources map array.
	 * 
	 * @param audio
	 * 				  {@link AudioSourceInterface} value
	 */
	void add(AudioSourceInterface audio);
	
	/**
	 * Returns audio source by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link AudioSourceInterface} value of chosen audio source
	 */
	AudioSourceInterface getByName(String name);
	
	/**
	 * Returns list of lights groupped by name.
	 * 
	 * @return {@link Collection}<{@link AudioSourceInterface}> value of audio sources
	 * 		   list
	 */
	Collection<AudioSourceInterface> getAll();	
	
	/**
	 * Returns audio master.
	 * 
	 * @return {@link AudioMasterInterface} value
	 */
	AudioMasterInterface getMaster();
	
	/**
	 * Clear all audio sources map and arrays.
	 */
	void clearAll();

}
