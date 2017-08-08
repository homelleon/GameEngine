package object.audio.manager;

import java.util.HashMap;
import java.util.Map;

import object.audio.master.IAudioMaster;
import object.audio.source.IAudioSource;
import tool.manager.AbstractManager;

/**
 * Audio manager for controling and storing structured map and arrays of audio
 * sources.
 * 
 * @author homelleon
 * @version 1.0
 */

public class AudioManager extends AbstractManager<IAudioSource> implements IAudioManager {

	private IAudioMaster audioMaster;

	public AudioManager(IAudioMaster audioMaster) {
		this.audioMaster = audioMaster;
	}

	@Override
	public IAudioMaster getMaster() {
		return audioMaster;
	}

	@Override
	public void clean() {
		super.clean();
		this.audioMaster.clear();
	}

}
