package audio;

import java.io.IOException;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {

		AudioMaster.init();
		AudioMaster.setListenerData(0,0,0);
		AL10.alDistanceModel(AL11.AL_LINEAR_DISTANCE_CLAMPED);
		
		int buffer = AudioMaster.loadSound("audio/birds006.wav");
		Source source = new Source();
		source.setLooping(true);
		source.play(buffer);
		
		float xPos = 8;
		source.setPosition(0, 0, 0);
		
	
		char c = ' ';
		while(c != 'q'){
			
			xPos -= 0.02f;
			source.setPosition(xPos, 0, 0);
			System.out.println(xPos);
			Thread.sleep(10);
			
		}
		
		source.delete();
		AudioMaster.cleanUp();

	}

}
