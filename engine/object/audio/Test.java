package object.audio;

import java.io.IOException;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.util.vector.Vector3f;

import object.audio.master.AudioMaster;
import object.audio.master.AudioMasterInterface;
import object.audio.source.AudioSource;
import object.audio.source.AudioSourceInterface;

/**
 * Runnable test class for Audio Engine.
 * <p>Uses few sound files to create audio buffers and play it during small time.
 * 
 * @author homelleon
 * 
 * @version 1.0
 *
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		AudioMasterInterface master = new AudioMaster();
		master.init();
		master.setListenerData(new Vector3f(0,0,0));
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		AudioSourceInterface source = new AudioSource("audio1", "forest.wav", 20, master);
		source.setLooping(true);
		source.play();
		
		
		float xPos = 8;
		
		while(xPos > -50) {
			xPos -= 0.0002f;
			source.setPosition(new Vector3f(xPos, 0, 2));
			System.out.println(xPos);
		}
		
		source.delete();
		master.cleanUp();
		master = null;
	}
	
}
