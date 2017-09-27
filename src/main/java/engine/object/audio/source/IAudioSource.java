package object.audio.source;

import object.Nameable;
import object.audio.master.AudioMaster;
import object.audio.master.IAudioMaster;
import tool.math.vector.Vector3fF;

/**
 * Interface of sound or music source in 3 dimentional space.
 * <p>
 * Can be used to set sounds in the game engine depending on position of the
 * source.
 * 
 * @author homelleon
 * @version 1.0
 * 
 * @see AudioSourceBuffered
 *
 */

public interface IAudioSource extends Nameable {

	/**
	 * Method to start sound source to play.
	 * <p>
	 * Whatever sound was paused by method {@link #pause()} it is still start
	 * playing from begining because method play() uses two other methods:
	 * {@link #stop()} and {@link #continuePlaying()}.
	 * 
	 * @see #pause()
	 * @see #stop()
	 * @see #continuePlaying()
	 */
	public void play(); // начать проигрывание адуио

	/**
	 * Method to delete audio source from audio buffer of
	 * {@link IAudioMaster}.
	 * 
	 * @see IAudioMaster
	 * @see AudioMaster
	 */
	public void delete();// удалить аудио

	/**
	 * Method to stop sound to play.
	 * <p>
	 * By this method Auidio Source remember the moment before stop and it can
	 * be continued after using method {@link #continuePlaying()}.
	 * 
	 * @see #stop()
	 * @see #continuePlaying()
	 * @see #play()
	 */
	public void pause(); // приостановить проигрывание аудио

	/**
	 * Method to start sound to play from moment it was paused using method
	 * {@link #pause()}.
	 */
	public void continuePlaying(); // продолжить проигрывание

	/**
	 * Method to stop sound to play.</br>
	 * Unlike {@link #pause()} method <b>stop()</b> don't make Audio Source to
	 * remember moment the sound stopped playing. That's why use of method
	 * {@link #continuePlaying()} make sound play from begining.
	 * 
	 * @see #pause()
	 * @see #play()
	 * @see #continuePlaying()
	 */
	public void stop(); // остановить проигрывание аудио

	/**
	 * Represents 3 dimentional speed of sound and sets how fast the sound will
	 * change in all 3 directions.
	 * 
	 * @param speed
	 *            Vec3f - 3 dimentional speed value
	 * @see #setPosition(Vector3fF)
	 */
	public void setVelocity(Vector3fF speed); // установить скорость аудио

	/**
	 * Method that defines if sound will be repeating after it was played till
	 * the end.
	 * 
	 * @param loop
	 *            boolean - defines if it repeats
	 */
	public void setLooping(boolean loop); // установить зацикливание

	/**
	 * Method that check AudioSource if it is playing at that moment.
	 * 
	 * @return true if it is playing</br>
	 *         false if it is stopped
	 * @see #play()
	 * @see #continuePlaying()
	 * @see #stop()
	 * @see #pause()
	 */
	public boolean isPlaying(); // вернуть, если проигрывается

	/**
	 * Change volume of the audio source.
	 * 
	 * @param volume
	 *            float value represents loudness of sound
	 * @see #setPitch(float)
	 */
	public void setVolume(float volume); // установить громкость

	/**
	 * Change pitch of audio source.
	 * 
	 * @param pitch
	 *            float value represents height of sound
	 * @see #setVolume(float)
	 */
	public void setPitch(float pitch); // установить высоту звука

	/**
	 * Change position of audio source in 3 dimentional space.
	 * 
	 * @param position
	 *            Vec3f representation of value to set
	 * @see #setVelocity(Vector3fF)
	 */
	public void setPosition(Vector3fF position); // установить позицию звука

	/**
	 * Returns position of sound source in 3 dimentional space.
	 * 
	 * @return 3 dimentional value of sound source position
	 */
	public Vector3fF getPosition();

}
