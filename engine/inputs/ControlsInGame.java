package inputs;

import org.lwjgl.input.Keyboard;

import entities.Entity;
import renderEngine.MasterRenderer;
import renderEngine.MasterRendererSimple;
import renderEngine.SceneRenderer;
import scene.ES;
import scene.Scene;

public class ControlsInGame implements Controls {
	
	private final int ECHO = 3;	
	private MouseGame mouse;	

	public ControlsInGame() {
		this.mouse = new MouseGame(ECHO);
	}

	@Override
	public void update(Scene scene, SceneRenderer renderer) {		
		pointedEntitiesControls(scene);
		sceneControls(renderer);
	}
	
	private void sceneControls(SceneRenderer renderer) {
		if(Keyboard.isKeyDown(ES.KEY_DEBUG_BOUNDING_BOX)) {
			MasterRendererSimple masterRenderer = renderer.getMasterRenderer();
			masterRenderer.switchBoundingBoxes(!masterRenderer.getShowBoundingBox());
		}
	}
	
	private void pointedEntitiesControls(Scene scene) {
		boolean isMousePointed = false;
		/* intersection of entities with mouse ray */
		//TODO: make class for control
		if (MouseGame.isOncePressed(MouseGame.LEFT_CLICK)) {	
			Entity pointedEntity = scene.getPicker().chooseObjectByRay(scene);
			if(pointedEntity != null) {
				scene.getEntities().addPointed(pointedEntity);
			}
			isMousePointed = true;			
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
