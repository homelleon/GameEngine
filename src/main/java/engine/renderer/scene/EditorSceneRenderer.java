package renderer.scene;

import control.ControlsImpl;
import control.Controls;
import renderer.MainRenderer;
import scene.Scene;

public class EditorSceneRenderer implements SceneRenderer {
	
	private MainRenderer mainRenderer;
	private Scene scene;
	private Controls controls;

	@Override
	public void initialize(Scene scene) {
		this.scene = scene;
		this.mainRenderer = new MainRenderer(scene);
		this.controls = new ControlsImpl();
	}

	@Override
	public void render(boolean isPaused) {
		checkInputs();
		move();
		renderToScreen();
	}

	private void checkInputs() {}

	private void renderToScreen() {
		mainRenderer.render(scene);
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
