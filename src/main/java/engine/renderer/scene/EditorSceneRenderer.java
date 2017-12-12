package renderer.scene;

import control.Controls;
import control.IControls;
import renderer.main.MainRenderer;
import scene.IScene;

public class EditorSceneRenderer implements ISceneRenderer {
	
	private MainRenderer mainRenderer;
	private IScene scene;
	private IControls controls;

	@Override
	public void initialize(IScene scene) {
		this.scene = scene;
		this.mainRenderer = new MainRenderer(scene);
		this.controls = new Controls();
	}

	@Override
	public void render(boolean isPaused) {
		checkInputs();
		move();
		renderToScreen();
	}

	private void checkInputs() {}

	private void renderToScreen() {
		mainRenderer.renderScene(scene);
	}

	private void move() {
		controls.update(scene);
		scene.getCamera().move();
	}

	@Override
	public MainRenderer getMainRenderer() {
		return this.mainRenderer;
	}

	@Override
	public void clean() {
		scene.clean();
		mainRenderer.clean();
	}

}
