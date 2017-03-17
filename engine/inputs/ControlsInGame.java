package inputs;

import org.lwjgl.input.Keyboard;

import debug.DebugUI;
import entities.Entity;
import scene.ES;
import scene.Scene;

public class ControlsInGame implements Controls {
	
	private final int ECHO = 3;	
	private MouseGame mouse;	

	public ControlsInGame() {
		this.mouse = new MouseGame(ECHO);
	}

	@Override
	public void update(Scene scene) {		
		pointedEntitiesControls(scene);
		sceneControls();
	}
	
	private void sceneControls() {
		if(KeyboardGame.isKeyPressed(ES.KEY_DEBUG_BOUNDING_BOX)) {
			DebugUI.switchBounding();
		}
		if(KeyboardGame.isKeyPressed(ES.KEY_DEBUG_INFORMATION)) {
			DebugUI.swithDebugInformation();
		}
	}
	
	private void pointedEntitiesControls(Scene scene) {
		/* intersection of entities with mouse ray */
		//TODO: make class for control
		if (MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {	
			Entity pointedEntity = scene.getPicker().chooseObjectByRay(scene);
			if(pointedEntity != null) {
				scene.getEntities().addPointed(pointedEntity);
			}		
		}
		
		if (MouseGame.isOncePressed(MouseGame.RIGHT_CLICK)) {
			scene.getEntities().clearPointed();
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_FORWARD)) {
			scene.getEntities().getPointed().forEach(i -> i.move(1, 0));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_BACKWARD)) {
			scene.getEntities().getPointed().forEach(i -> i.move(-1, 0));
		}		
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_LEFT)) {
			scene.getEntities().getPointed().forEach(i -> i.move(0, 1));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_RIGHT)) {
			scene.getEntities().getPointed().forEach(i -> i.move(0, -1));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_UP)) {
			scene.getEntities().getPointed().forEach(i -> i.increasePosition(0, 1, 0));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_MOVE_DOWN)) {
			scene.getEntities().getPointed().forEach(i -> i.increasePosition(0, -1, 0));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_ROTATE_LEFT)) {
			scene.getEntities().getPointed().forEach(i -> i.increaseRotation(0, 1, 0));
		}
		
		if (Keyboard.isKeyDown(ES.KEY_OBJECT_ROTATE_RIGHT)) {
			scene.getEntities().getPointed().forEach(i -> i.increaseRotation(0, -1, 0));
		}
		
	}
	

}
