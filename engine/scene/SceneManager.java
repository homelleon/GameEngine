package scene;

import renderEngine.Loader;

/**
 * Temporal interface to initialize test objects. 
 * 
 * @author homelleon
 *
 */
public interface SceneManager {
	
	/**
	 * Initialize test objects in the scene.
	 *  
	 * @param scene
	 * 				{@link Scene} value
	 * @param loader
	 * 				{@link Loader} value
	 */
	public void init(Scene scene, Loader loader);

}
