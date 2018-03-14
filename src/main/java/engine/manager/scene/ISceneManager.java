package manager.scene;

import primitive.buffer.Loader;
import scene.Scene;

/**
 * Temporal interface to initialize test objects.
 * 
 * @author homelleon
 * @see SceneManager
 *
 */
public interface ISceneManager {

	/**
	 * Initialize test objects in the scene.
	 * 
	 * @param scene
	 *            {@link IScene} value
	 * @param loader
	 *            {@link Loader} value
	 */
	public void init(Scene scene, int mode);

}
