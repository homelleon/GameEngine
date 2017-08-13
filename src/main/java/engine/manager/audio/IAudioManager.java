package manager.audio;

import object.audio.master.IAudioMaster;
import object.audio.source.IAudioSource;
import tool.manager.IManager;

/**
 * Interface to store and control audio sources.
 * 
 * @author homelleon
 *
 */

public interface IAudioManager extends IManager<IAudioSource> {

	/**
	 * Returns audio master.
	 * 
	 * @return {@link IAudioMaster} value
	 */
	IAudioMaster getMaster();

}
