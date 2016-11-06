package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import maps.GameMap;
import renderEngine.DisplayManager;
import scene.GameSceneRenderer;
import scene.WorldGethable;
import triggers.Trigger;

public class MainGameLoop implements Engine {
	
	private WorldGethable scene;
	private boolean isScenePaused = false;
	
	public MainGameLoop() {
		DisplayManager.creatDisplay();
		scene = new GameSceneRenderer();
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
			
			if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
				isScenePaused = !isScenePaused;
				scene.setScenePaused(isScenePaused);
			}
			
		}
		
		scene.cleanUp();
		DisplayManager.closeDisplay();
		
	}
	
	public GameMap getMap() {
		return scene.getMap();
	}
	

	@Override
	public void exit() {
		Display.destroy();		
	}



}
