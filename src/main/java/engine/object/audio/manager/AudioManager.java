package object.audio.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
		} else {
			throw new NullPointerException("Trying to add null value into AudioManager array!");
		}
	}
	
	@Override
	public void addAll(List<IAudioSource> audioList) {
		if ((audioList != null) && (!audioList.isEmpty())) {
			for (IAudioSource audio : audioList) {
				this.audioSources.put(audio.getName(), audio);
			}
		} else {
			throw new NullPointerException("Trying to add null value into AudioManager array!");
		}
	}

	@Override
	public void add(IAudioSource audio) {
		if (audio != null) {
			this.audioSources.put(audio.getName(), audio);
		} else {
			throw new NullPointerException("Trying to add null value into AudioManager array!");
		}
	}

	@Override
	public IAudioSource get(String name) {
		return this.audioSources.get(name);
	}

	@Override
	public Collection<IAudioSource> getAll() {
		return this.audioSources.values();
	}
	

	@Override
	public boolean delete(String name) {
		if(this.audioSources.containsKey(name)) {
			this.audioSources.remove(name);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public IAudioMaster getMaster() {
		return audioMaster;
	}

	@Override
	public void clean() {
		this.audioMaster.clear();
		this.audioSources.clear();
	}

}
