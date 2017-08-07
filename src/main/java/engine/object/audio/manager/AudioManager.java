package object.audio.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import object.audio.master.IAudioMaster;
import object.audio.source.IAudioSource;

/**
 * Audio manager for controling and storing structured map and arrays of audio
 * sources.
 * 
 * @author homelleon
 * @version 1.0
 */

public class AudioManager implements IAudioManager {

	private Map<String, IAudioSource> audioSources = new HashMap<String, IAudioSource>();
	private IAudioMaster audioMaster;

	public AudioManager(IAudioMaster audioMaster) {
		this.audioMaster = audioMaster;
	}

	@Override
	public void addAll(Collection<IAudioSource> audioList) {
		if ((audioList != null) && (!audioList.isEmpty())) {
			for (IAudioSource audio : audioList) {
				this.audioSources.put(audio.getName(), audio);
			}
		}
	}

	@Override
	public void add(IAudioSource audio) {
		if (audio != null) {
			this.audioSources.put(audio.getName(), audio);
		}
	}

	@Override
	public IAudioSource getByName(String name) {
		IAudioSource audio = null;
		if (this.audioSources.containsKey(name)) {
			audio = this.audioSources.get(name);
		}
		return audio;
	}

	@Override
	public Collection<IAudioSource> getAll() {
		return this.audioSources.values();
	}

	@Override
	public IAudioMaster getMaster() {
		return audioMaster;
	}

	@Override
	public void clean() {
		this.audioMaster.cleanUp();
		this.audioSources.clear();
	}

}
