package control;

import org.lwjgl.input.Keyboard;

import core.EngineMain;
import core.Loop;
import core.settings.EngineSettings;
import manager.ObjectMapManager;
import manager.scene.ObjectManager;
import object.entity.Entity;
import scene.Scene;
import scene.writer.LevelMapWriter;
import scene.writer.LevelMapXMLWriter;

/**
 * Engine UI and object controls.
 *  
 * @author homelleon
 * @version 1.0
 */
public class ControlsImpl implements Controls {

	private final int ECHO = 3;
	private MouseGame mouse;

	public ControlsImpl() {
		this.mouse = new MouseGame(ECHO);
	}

	@Override
	public void update(Scene scene) {
		pointedEntitiesControls(scene);
		sceneControls(scene);
	}

	private void sceneControls(Scene scene) {
		if(KeyboardGame.isKeyPressed(Keyboard.KEY_ESCAPE)) {
			if(!Loop.getInstance().getEditMode()) {
				EngineMain.exit();
			}
		}
		
		if (KeyboardGame.isKeyPressed(EngineSettings.KEY_PAUSE)) {
			EngineMain.pauseEngine(!EngineMain.getIsEnginePaused());
		}
		
		if (KeyboardGame.isKeyPressed(EngineSettings.KEY_SAVE)) {
			EngineMain.pauseEngine(true);
			LevelMapWriter mapWriter = new LevelMapXMLWriter();
			ObjectManager map = new ObjectMapManager();
			map.getEntities().addAll(scene.getEntities().getAll());
			map.getTerrains().addAll(scene.getTerrains().getAll());
			mapWriter.write(map);
			EngineMain.pauseEngine(false);
		}
		
	}

	private void pointedEntitiesControls(Scene scene) {
		/* intersection of entities with mouse ray */
		// TODO: make class for control
		if (MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {
			if(!Loop.getInstance().getEditMode()) {
				Entity pointedEntity = scene.getMousePicker().chooseObjectByRay(scene);
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