package manager;

import object.audio.AudioMaster;
import object.audio.AudioSource;
import tool.manager.AbstractManager;

/**
 * Audio manager for controling and storing structured map and arrays of audio
 * sources.
 * 
 * @author homelleon
 * @version 1.0
 */

public class AudioManager extends AbstractManager<AudioSource> {

	private AudioMaster audioMaster;

	public AudioManager(AudioMaster audioMaster) {
		this.audioMaster = audioMaster;
	}

	public AudioMaster getMaster() {
		return audioMaster;
	}

	@Override
	public void clean() {
		elements.values().forEach(AudioSource::delete);
		super.clean();
		if (audioMaster!= null)
			audioMaster.clear();
	}

}
