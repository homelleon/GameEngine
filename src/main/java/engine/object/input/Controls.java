package object.input;

import org.lwjgl.input.Keyboard;

import core.debug.EngineDebug;
import core.settings.EngineSettings;
import object.entity.entity.IEntity;
import object.scene.scene.IScene;

public class Controls implements IControls {

	private final int ECHO = 3;
	private MouseGame mouse;

	public Controls() {
		this.mouse = new MouseGame(ECHO);
	}

	@Override
	public void update(IScene scene) {
		pointedEntitiesControls(scene);
		sceneControls();
	}

	private void sceneControls() {
		if (KeyboardGame.isKeyPressed(EngineSettings.KEY_DEBUG_BOUNDING_BOX)) {
			EngineDebug.switchBounding();
		}
		if (KeyboardGame.isKeyPressed(EngineSettings.KEY_DEBUG_INFORMATION)) {
			EngineDebug.switchDebugInformation();
		}
	}

	private void pointedEntitiesControls(IScene scene) {
		/* intersection of entities with mouse ray */
		// TODO: make class for control
		if (MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {
			IEntity pointedEntity = scene.getPicker().chooseObjectByRay(scene);
			if (pointedEntity != null) {
				scene.getEntities().addPointed(pointedEntity);
			}
		}

		if (MouseGame.isOncePressed(MouseGame.RIGHT_CLICK)) {
			scene.getEntities().clearPointed();
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_FORWARD)) {
			scene.getEntities().getPointed().forEach(i -> i.move(1, 0));
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_BACKWARD)) {
			scene.getEntities().getPointed().forEach(i -> i.move(-1, 0));
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_LEFT)) {
			scene.getEntities().getPointed().forEach(i -> i.move(0, 1));
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_RIGHT)) {
			scene.getEntities().getPointed().forEach(i -> i.move(0, -1));
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_UP)) {
			scene.getEntities().getPointed().forEach(i -> i.increasePosition(0, 1, 0));
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_DOWN)) {
			scene.getEntities().getPointed().forEach(i -> i.increasePosition(0, -1, 0));
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_ROTATE_LEFT)) {
			scene.getEntities().getPointed().forEach(i -> i.increaseRotation(0, 1, 0));
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_ROTATE_RIGHT)) {
			scene.getEntities().getPointed().forEach(i -> i.increaseRotation(0, -1, 0));
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			scene.spreadEntitiesOnHeights(scene.getEntities().getPointed());
		}

	}

}
