package renderer.scene;

import object.input.Controls;
import object.input.IControls;
import object.scene.IScene;
import renderer.main.MainRenderer;

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
	public MainRenderer getMasterRenderer() {
		return this.mainRenderer;
	}

	@Override
	public void clean() {
		scene.clean();
		mainRenderer.clean();
	}

}
