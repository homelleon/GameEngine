package audio;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
import org.lwjgl.util.vector.Vector3f;

import scene.ES;


public class AudioMaster { 
	
	private static Map<String, Integer> buffers = new HashMap<String, Integer>();

	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}	
	
	public static Map<String, Integer> getBuffers() {
		return buffers;
	}

	public static int getBuffer(String path) {
		return buffers.get(path);
	}
		

	public static void setListenerData(float x, float y, float z) {
		AL10.alListener3f(AL10.AL_POSITION, x, y, z);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	public static void setListenerData(Vector3f position) {
		AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, position.z);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	public static int loadSound(String file) {
		int buffer = AL10.alGenBuffers(); 
		buffers.put(file, buffer);
		
		WaveData waveFile = WaveData.create(ES.AUDIO_PATH + file);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		return buffer;
	}
	
	public static void cleanUp() {
		for(int buffer : buffers.values()) {
			AL10.alDeleteBuffers(buffer);
		}
		AL.destroy();
	}
	
}
