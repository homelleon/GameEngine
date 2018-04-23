package manager.scene;

import primitive.buffer.Loader;
import scene.Scene;

/**
 * Temporal interface to initialize test objects.
 * 
 * @author homelleon
 * @see SceneFactoryImpl
 *
 */
public interface SceneFactory {

	/**
	 * Initialize test objects in the scene.
	 * 
	 * @param scene
	 *            {@link IScene} value
	 * @param loader
	 *            {@link Loader} value
	 */
	public Scene create(ObjectManager levelMap, int mode);
	
	public Scene getScene();

}
