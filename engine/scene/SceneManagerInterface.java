package scene;

import renderEngine.Loader;

/**
 * Temporal interface to initialize test objects. 
 * 
 * @author homelleon
 * @see SceneManager
 *
 */
public interface SceneManagerInterface {
	
	/**
	 * Initialize test objects in the scene.
	 *  
	 * @param scene
	 * 				{@link SceneInterface} value
	 * @param loader
	 * 				{@link Loader} value
	 */
	public void init(SceneInterface scene, Loader loader);

}
