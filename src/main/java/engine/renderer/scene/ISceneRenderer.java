package renderer.scene;

import object.scene.IScene;
import renderer.main.MainRenderer;

/**
 * 
 * @author homelleon
 * @see GameSceneRenderer
 * @see EditorSceneRenderer
 */
public interface ISceneRenderer {
	
	public void initialize(IScene scene);

	public void render(boolean isPaused);
	
	public MainRenderer getMasterRenderer();

	public void clean();

}
