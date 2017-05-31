package objects.audio;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.vector.Vector3f;

import core.settings.ES;

/**
 * Class of 3 dimentional sound engine storeing audio buffers in the map.
 * 
 * @author homelleon 
 * @version 1.0
 * @see AudioMasterInterface
 *
 */

public class AudioMaster implements AudioMasterInterface { 
	
	/**
	 * Represents a map of audio buffers stored in OpenAL engine.
	 */
	private Map<String, Integer> buffers = new HashMap<String, Integer>();
	
	@Override
	public void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public Map<String, Integer> getBuffers() {
		return buffers;
	}
	
	@Override
	public int getBuffer(String file) {
		return buffers.get(file);
	}	
	
	@Override
	public void setListenerData(Vector3f position) {
		AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, position.z);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}	
	
	@Override
	public int loadSound(String file) {
		int buffer = AL10.alGenBuffers(); 
		buffers.put(file, buffer);
		
		WaveData waveFile = WaveData.create(ES.AUDIO_PATH + file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return buffer;
	}	
	
	@Override
	public void cleanUp() {
		for(int buffer : buffers.values()) {
			AL10.alDeleteBuffers(buffer);
		}
		AL.destroy();
	}
	
}
