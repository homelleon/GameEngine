package manager.scene;

import object.scene.IScene;
import renderer.loader.Loader;

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
	public void init(IScene scene, int mode);

}
