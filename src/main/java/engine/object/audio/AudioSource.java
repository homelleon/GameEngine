package object.audio;

import org.lwjgl.openal.AL10;

import scene.Subject;
import tool.math.vector.Vector3f;

/**
 * Simple type of Audio Source using name, sourceId, fileName to represent the
 * source of sound or music in 3 dimentions.
 * 
 * @author homelleon
 * @version 1.0
 *
 */

public class AudioSource extends Subject<Vector3f> {
	
	private int sourceId;
	private String fileName;
	private AudioMaster master;

	/**
	 * Constructor of AudioSource object without changing position value.
	 * 
	 * @param name
	 *            String value of audio source
	 * @param file
	 *            String value of audio file name
	 * @param maxDistance
	 *            int value of maximum distance that audio source has effect
	 * @param master
	 *            AudioMaster object needed to check same audio files and store
	 *            audio source with same audio track in the same list of auido
	 *            buffer map
	 * @see IAudioSource
	 * @see #AudioSourceSimple(String, String, int, Vector3f,
	 *      IAudioMaster)
	 */
	public AudioSource(String name, String file, int maxDistance, AudioMaster master) {
		super(name);
		sourceId = AL10.alGenSources();
		AL10.alSourcef(sourceId, AL10.AL_ROLLOFF_FACTOR, 1);
		AL10.alSourcef(sourceId, AL10.AL_REFERENCE_DISTANCE, 6);
		AL10.alSourcef(sourceId, AL10.AL_MAX_DISTANCE, maxDistance);
		this.name = name;
		this.master = master;
		if (!master.getBuffers().containsKey(file)) {
			master.loadSound(file);
		}
		this.fileName = file;
	}

	/**
	 * Constuctor of AudioSource object with assigning a position value.
	 * 
	 * @param name
	 *            String value of audio source
	 * @param file
	 *            String value of audio file name
	 * @param maxDistance
	 *            int value of maximum distance that audio source has effect
	 * @param position
	 *            Vec3f value of audio source position in 3 dimentional space
	 * @param master
	 *            AudioMaster object needed to check same audio files and store
	 *            audio source with same audio track in the same list of auido
	 *            buffer map
	 * @see IAudioSource
	 * @see #AudioSourceSimple(String, String, int, IAudioMaster)
	 */
	public AudioSource(String name, String file, int maxDistance, Vector3f position, AudioMaster master) {
		super(name);
		sourceId = AL10.alGenSources();
		AL10.alSourcef(sourceId, AL10.AL_ROLLOFF_FACTOR, 1);
		AL10.alSourcef(sourceId, AL10.AL_REFERENCE_DISTANCE, 6);
		AL10.alSourcef(sourceId, AL10.AL_MAX_DISTANCE, maxDistance);
		this.name = name;
		this.master = master;
		if (!master.getBuffers().containsKey(file)) {
			master.loadSound(file);
		}
		this.fileName = file;
		setPosition(position);
	}

	public void play() {
		stop();
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, master.getBuffer(fileName));
		continuePlaying();
	}

	public void delete() {
		stop();
		AL10.alDeleteSources(sourceId);
	}

	public void pause() {
		AL10.alSourcePause(sourceId);
	}

	public void continuePlaying() {
		AL10.alSourcePlay(sourceId);
	}

	public void stop() {
		AL10.alSourceStop(sourceId);
	}

	public void setVelocity(Vector3f speed) {
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, speed.x, speed.y, speed.z);
	}

	public void setLooping(boolean loop) {
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
	}

	public boolean isPlaying() {
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	public void setVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}

	public void setPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}

	@Override
	public AudioSource setPosition(Vector3f position) {
		AL10.alSource3f(sourceId, AL10.AL_POSITION, position.x, position.y, position.z);
		return (AudioSource) super.setPosition(position);
	}

}
