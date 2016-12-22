package audio;

import java.io.IOException;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		AudioSource source = new AudioSource("audio1", "forest.wav", 20);
		source.setLooping(true);
		source.play();
		
		
		float xPos = 8;
		
		while(xPos > -50) {
			xPos -= 0.0002f;
			source.setPosition(xPos, 0, 2);
			System.out.println(xPos);
		}
		
		source.delete();
		AudioMaster.cleanUp();
	}
	
}
