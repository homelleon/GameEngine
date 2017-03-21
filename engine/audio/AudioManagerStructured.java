package audio;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Audio manager for controling and storing structured map and arrays of 
 * audio sources.
 * 
 * @author homelleon
 * @version 1.0
 */

public class AudioManagerStructured implements AudioManager {
	
	private Map<String, AudioSource> audioSources = new HashMap<String, AudioSource>();
	private AudioMaster audioMaster;
	
	public AudioManagerStructured(AudioMaster audioMaster) {
		this.audioMaster = audioMaster;
	}


	@Override
	public void addAll(Collection<AudioSource> audioList) {
		if((audioList != null) && (!audioList.isEmpty())) {
			for(AudioSource audio : audioList) {
				this.audioSources.put(audio.getName(), audio);
			}
		}	
	}

	@Override
	public void add(AudioSource audio) {
		if(audio != null) {
			this.audioSources.put(audio.getName(), audio); 		
		}
	}

	@Override
	public AudioSource getByName(String name) {
		AudioSource audio = null;
		if(this.audioSources.containsKey(name)) {
			audio = this.audioSources.get(name);
		}
		return audio;
	}

	@Override
	public Collection<AudioSource> getAll() {
		return this.audioSources.values();
	}
	
	
	@Override
	public AudioMaster getMaster() {
		return audioMaster;
	}

	@Override
	public void clearAll() {
		this.audioMaster.cleanUp();
		this.audioSources.clear();
	}



}
