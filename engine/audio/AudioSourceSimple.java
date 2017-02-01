package audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

/*
 *  AduioSource - источник аудио
 *  02.01.17
 * ------------------------------
*/

public class AudioSourceSimple implements AudioSource {
	
	private String name; //имя аудио
	private int sourceId; //ID аудио
	private String fileName;  //расположение файла
	private AudioMaster master; //аудио-мастер
	
	/** конструктор
	 * @param name 
	 * 				- имя источника
	 * @param file
	 * 				- имя файла
	 * @param maxDistance
	 * 				- дистанция слышимости
	 * @param master
	 * 				- аудио-мастер
	 */
	public AudioSourceSimple(String name, String file, int maxDistance, AudioMaster master) {
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
	
	/** конструктор с расположением источника в мире
	 * @param name 
	 * 				- имя источника
	 * @param file
	 * 				- имя файла
	 * @param maxDistance
	 * 				- дистанция слышимости
	 * @param position
	 * 				- расположение источника в пространстве
	 * @param master
	 * 				- аудио-мастер
	 */
	public AudioSourceSimple(String name, String file, int maxDistance, Vector3f position, 
			AudioMaster master) {
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
		this.setPosition(position);
	}	
	
	//вернуть имя аудио
	public String getName() {
		return name;
	}
	
	//начать проигрывание аудио
	public void play() {
		stop();
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, master.getBuffer(fileName));
		continuePlaying();
	}
	
	//удалить аудио
	public void delete() {
		stop();
		AL10.alDeleteSources(sourceId);
	}
	
	//приостановить проигрывание аудио
	public void pause() {
		AL10.alSourcePause(sourceId);
	}
	
	//продолжить проигрывание аудио с предыдущего момента
	public void continuePlaying() {
		AL10.alSourcePlay(sourceId);
	}
	
	//остановить проигрывание аудио
	public void stop() {
		AL10.alSourceStop(sourceId);
	}
	
	//установить скорость аудио
	public void setVelocity(Vector3f speed) {
		AL10.alSource3f(sourceId, AL10.AL_VELOCITY, speed.x, speed.y, speed.z);
	}
	
	//установить зацикливание
	public void setLooping(boolean loop) {
		AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
	}
	
	//вернуть, если проигрывается
	public boolean isPlaying() {
		return AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}
	
	//установить громкость
	public void setVolume(float volume) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
	}
	
	//установить высоту звука
	public void setPitch(float pitch) {
		AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
	}
	
	//установить позицию звука
	public void setPosition(Vector3f position) {
		AL10.alSource3f(sourceId, AL10.AL_POSITION, 
				position.x, position.y, position.z);
	}
	
	
}
