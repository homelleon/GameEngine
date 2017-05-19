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

public class AudioManager implements AudioManagerInterface {
	
	private Map<String, AudioSourceInterface> audioSources = new HashMap<String, AudioSourceInterface>();
	private AudioMasterInterface audioMaster;
	
	public AudioManager(AudioMasterInterface audioMaster) {
		this.audioMaster = audioMaster;
	}


	@Override
	public void addAll(Collection<AudioSourceInterface> audioList) {
		if((audioList != null) && (!audioList.isEmpty())) {
			for(AudioSourceInterface audio : audioList) {
				this.audioSources.put(audio.getName(), audio);
			}
		}	
	}

	@Override
	public void add(AudioSourceInterface audio) {
		if(audio != null) {
			this.audioSources.put(audio.getName(), audio); 		
		}
	}

	@Override
	public AudioSourceInterface getByName(String name) {
		AudioSourceInterface audio = null;
		if(this.audioSources.containsKey(name)) {
			audio = this.audioSources.get(name);
		}
		return audio;
	}

	@Override
	public Collection<AudioSourceInterface> getAll() {
		return this.audioSources.values();
	}
	
	
	@Override
	public AudioMasterInterface getMaster() {
		return audioMaster;
	}

	@Override
	public void clearAll() {
		this.audioMaster.cleanUp();
		this.audioSources.clear();
	}



}
