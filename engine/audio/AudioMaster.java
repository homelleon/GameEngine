package audio;

import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

/**
 * Manages the audio used in OpenAL library that represent 3 dimentional
 * sound engine.
 * 
 * @author homelleon
 * @version 1.0 
 */
public interface AudioMaster {
	
	/** Initialization of Audio Master creating OpenAL context. */
	 void init(); 
	 
	 /** 
	  * Returns map of all buffers in Audio Master.
	  * 
	  * @return Map<String, Integer> 
   									audio buffers 
	  */
	 public Map<String, Integer> getBuffers();
	 
	 /** 
	  * Returns auido buffer by path of audio
	  *  
	  * @param path 
	  * 			position of audio file
	  * @return int 
	  * 			buffer ID
	  */
	 int getBuffer(String path); 
	 
	 /** 
	  * Setting position of user in 3 dimentional space.
	  * 
	  * @param position
	  * 				Vector3f
	  */
	 void setListenerData(Vector3f position);
	 
	 /**
	  * Loads sound file into audio buffer map and sets key with the name of 
	  * the file.
	  * 
	  * @param file
	  * 			String name of file to load
	  * @return int
	  * 			buffer ID of loaded file
	  */
	 int loadSound(String file);
	 
	 /**
	  * Clean audio buffer and destroy OpenAL context.
	  */
	 void cleanUp(); 

}
