package object.audio.manager;

import java.util.Collection;

import object.audio.master.IAudioMaster;
import object.audio.source.IAudioSource;

/**
 * Interface to store and control audio sources.
 * 
 * @author homelleon
 *
 */

public interface IAudioManager {

	/**
	 * Adds list of audio sources into audio sources map array.
	 * 
	 * @param audioList
	 *            {@link Collection}<{@link IAudioSource}> value of
	 *            audio sources list
	 */
	void addAll(Collection<IAudioSource> audioList);

	/**
	 * Adds one audio source into audio sources map array.
	 * 
	 * @param audio
	 *            {@link IAudioSource} value
	 */
	void add(IAudioSource audio);

	/**
	 * Returns audio source by name.
	 * 
	 * @param name
	 *            {@link String} value
	 * 
	 * @return {@link IAudioSource} value of chosen audio source
	 */
	IAudioSource getByName(String name);

	/**
	 * Returns list of lights groupped by name.
	 * 
	 * @return {@link Collection}<{@link IAudioSource}> value of audio
	 *         sources list
	 */
	Collection<IAudioSource> getAll();

	/**
	 * Returns audio master.
	 * 
	 * @return {@link IAudioMaster} value
	 */
	IAudioMaster getMaster();

	/**
	 * Clear all audio sources map and arrays.
	 */
	void clean();

}
