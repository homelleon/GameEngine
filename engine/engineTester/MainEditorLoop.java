package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import maps.GameMap;
import renderEngine.DisplayManager;
import scene.SceneRenderer;
import scene.WorldGethable;

public class MainEditorLoop implements Engine {
	
	private WorldGethable scene;
	
	public MainEditorLoop() {
		DisplayManager.creatDisplay(1);
		scene = new SceneRenderer(true);		
	}

	@Override
	public void LoadMap(String name) {
		scene.loadMap(name);				
	}

	@Override
	public void init() {
		scene.init();		
	}

	@Override
	public void run() {
		while(!Display.isCloseRequested()) {
			scene.render();
			DisplayManager.updateDisplay(); 
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				break;
			}
			
		}
		
		scene.cleanUp();
		DisplayManager.closeDisplay();		
	}

	@Override
	public void exit() {
		Display.destroy();		
	}

	@Override
	public GameMap getMap() {
		return scene.getMap();
	}

}
