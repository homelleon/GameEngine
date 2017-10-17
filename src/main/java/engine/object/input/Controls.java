package object.input;

import org.lwjgl.input.Keyboard;

import core.EngineMain;
import core.debug.EngineDebug;
import core.loop.Loop;
import core.settings.EngineSettings;
import object.entity.entity.IEntity;
import object.scene.IScene;

public class Controls implements IControls {

	private final int ECHO = 3;
	private MouseGame mouse;

	public Controls() {
		this.mouse = new MouseGame(ECHO);
	}

	@Override
	public void update(IScene scene) {
		debugControls();
		pointedEntitiesControls(scene);
		sceneControls();
	}
	
	private void debugControls() {		
		if(EngineDebug.hasDebugPermission()) {
			if (KeyboardGame.isKeyPressed(EngineSettings.KEY_DEBUG_INFORMATION)) {
				EngineDebug.switchDebugPermission();
			}
			
			if (KeyboardGame.isKeyPressed(EngineSettings.KEY_DEBUG_BOUNDING_BOX)) {
				EngineDebug.switchBounding();
			}
			
			if(KeyboardGame.isKeyPressed(EngineSettings.KEY_DEBUG_WIRED_FRAME)) {
				EngineMain.switchWiredFrameMode();
			}
		}
	}

	private void sceneControls() {
		if(KeyboardGame.isKeyPressed(Keyboard.KEY_ESCAPE)) {
			if(!Loop.getInstance().getEditMode()) {
				EngineMain.exit();
			}
		}
		if (KeyboardGame.isKeyPressed(EngineSettings.KEY_PAUSE)) {
			EngineMain.pauseEngine(!EngineMain.getIsEnginePaused());
		}
		
	}

	private void pointedEntitiesControls(IScene scene) {
		/* intersection of entities with mouse ray */
		// TODO: make class for control
		if (MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {
			if(!Loop.getInstance().getEditMode()) {
				IEntity pointedEntity = scene.getMousePicker().chooseObjectByRay(scene);
				if (pointedEntity != null && !scene.getEntities().getPointed().contains(pointedEntity)) {
					scene.getEntities().addPointed(pointedEntity);
				}
			}
		}

		if (MouseGame.isOncePressed(MouseGame.RIGHT_CLICK)) {
			scene.getEntities().clearPointed();
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_FORWARD)) {
			scene.getEntities().getPointed().forEach(entity -> {
				entity.move(1, 0);
				scene.getFrustumEntities().addEntityInNodes(entity);	
			});
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_BACKWARD)) {
			scene.getEntities().getPointed().forEach(entity -> {				
				entity.move(-1, 0);
				scene.getFrustumEntities().addEntityInNodes(entity);
			});
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_LEFT)) {
			scene.getEntities().getPointed().forEach(entity -> {
				entity.move(0, 1);
				scene.getFrustumEntities().addEntityInNodes(entity);
			});
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_RIGHT)) {
			scene.getEntities().getPointed().forEach(entity -> {
				entity.move(0, -1);
				scene.getFrustumEntities().addEntityInNodes(entity);
			});
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_UP)) {
			scene.getEntities().getPointed().forEach(entity -> {
				entity.increasePosition(0, 1, 0);
				scene.getFrustumEntities().addEntityInNodes(entity);
			});
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_MOVE_DOWN)) {
			scene.getEntities().getPointed().forEach(entity -> {
				entity.increasePosition(0, -1, 0);
				scene.getFrustumEntities().addEntityInNodes(entity);
			});
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_ROTATE_LEFT)) {
			scene.getEntities().getPointed().forEach(entity -> entity.increaseRotation(0, 2, 0));
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_OBJECT_ROTATE_RIGHT)) {
			scene.getEntities().getPointed().forEach(entity -> entity.increaseRotation(0, -2, 0));
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			scene.spreadEntitiesOnHeights(scene.getEntities().getPointed());
		}

	}

}
