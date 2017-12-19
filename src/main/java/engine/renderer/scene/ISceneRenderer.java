package renderer.scene;

import renderer.main.MainRenderer;
import scene.IScene;

/**
 * 
 * @author homelleon
 * @see GameSceneRenderer
 * @see EditorSceneRenderer
 */
public interface ISceneRenderer {
	
	public void initialize(IScene scene);

	public void render(boolean isPaused);
	
	public MainRenderer getMainRenderer();

	public void clean();

}
