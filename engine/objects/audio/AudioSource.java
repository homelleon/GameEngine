package objects.audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

/**
 * Simple type of Audio Source using name, sourceId, fileName to represent the
 * source of sound or music in 3 dimentions.
 * 
 * @author homelleon
 * @version 1.0 
 *
 */

public class AudioSource implements AudioSourceInterface {
	
	private String name; //имя аудио
	private int sourceId; //ID аудио
	private String fileName;  //расположение файла
	private AudioMasterInterface master; //аудио-мастер
	private Vector3f position; //позиция аудио-источника
	
	/** Constructor of AudioSource object without changing position value. 
	 *   
	 * @param name 
	 * 						String value of audio source
	 * @param file
	 * 						String value of audio file name 
	 * @param maxDistance
	 * 						int value of maximum distance that audio source
	 * 						has effect
	 * @param master
	 * 						AudioMaster object needed to check same audio
	 * 						files and store audio source with same audio
	 * 						track in the same list of auido buffer map
	 * @see AudioSourceInterface
	 * @see #AudioSourceSimple(String, String, int, Vector3f, AudioMasterInterface)
	 */
	public AudioSource(String name, String file, int maxDistance, AudioMasterInterface master) {
		sourceId = AL10.alGenSources();
		AL10.alSourcef(sourceId, AL10.AL_ROLLOFF_FACTOR, 1);
		AL10.alSourcef(sourceId, AL10.AL_REFERENCE_DISTANCE, 6);
		AL10.alSourcef(sourceId, AL10.AL_MAX_DISTANCE, maxDistance);
		this.name = name;		
		this.master = master;
		if (!master.getBuffers().containsKey(file)){
			master.loadSound(file);
		}
		this.fileName = file;
	}
	
	/** Constuctor of AudioSource object with assigning a position value.
	 * @param name 
	 * 						String value of audio source
	 * @param file
	 * 						String value of audio file name
	 * @param maxDistance
	 * 						int value of maximum distance that audio source
	 * 						has effect
	 * @param position
	 * 						Vector3f value of audio source position in 3
	 * 						dimentional space
	 * @param master
	 * 						AudioMaster object needed to check same audio
	 * 						files and store audio source with same audio
	 * 						track in the same list of auido buffer map
	 * @see AudioSourceInterface
	 * @see #AudioSourceSimple(String, String, int, AudioMasterInterface)
	 */
	public AudioSource(String name, String file, int maxDistance, Vector3f position, 
			AudioMasterInterface master) {
		sourceId = AL10.alGenSources();
		AL10.alSourcef(sourceId, AL10.AL_ROLLOFF_FACTOR, 1);
		AL10.alSourcef(sourceId, AL10.AL_REFERENCE_DISTANCE, 6);
		AL10.alSourcef(sourceId, AL10.AL_MAX_DISTANCE, maxDistance);
		this.name = name;
		this.master = master;
		if (!master.getBuffers().containsKey(file)){
			master.loadSound(file);
		}
		this.fileName = file;
		this.position = position;
		this.setPosition(position);
	}	
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void play() {
		stop();
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, master.getBuffer(fileName));
		continuePlaying();
	}
	
	@Override
	public void delete() {
		stop();
		AL10.alDeleteSources(sourceId);
	}
	
	@Override
	public void pause() {
		AL10.alSourcePause(sourceId);
	}
	
	@Override
	public void continuePlaying() {
		AL10.alSourcePlay(sourceId);
	}
	
	@Override
	public void stop() {
		AL10.alSourceStop(sourceId);
	}
	
	@Override
	public void setVelocity(Vector3f speed) {
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, speed.x, speed.y, speed.z);
	}
	
	@Override
	public void setLooping(boolean loop) {
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
	
	@Override
	public boolean isPlaying() {
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	@Override
	public void setVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}
	
	@Override
	public void setPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
		AL10.alSource3f(sourceId, AL10.AL_POSITION, 
				position.x, position.y, position.z);
	}

	@Override
	public Vector3f getPosition() {
		return this.position;
	}
	
	
}
