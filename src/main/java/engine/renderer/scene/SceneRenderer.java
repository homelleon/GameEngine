package renderer.scene;

import renderer.MainRenderer;
import scene.Scene;

/**
 * 
 * @author homelleon
 * @see GameSceneRenderer
 * @see EditorSceneRenderer
 */
public interface SceneRenderer {
	
	public void initialize(Scene scene);

	public void render(boolean isPaused);
	
	public MainRenderer getMainRenderer();

	public void clean();

}
