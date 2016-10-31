package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import maps.GameMap;
import renderEngine.DisplayManager;
import scene.SceneRenderer;
import scene.WorldGethable;
import triggers.Trigger;

public class MainGameLoop implements Engine {
	
	private WorldGethable scene;
	
	public MainGameLoop() {
		DisplayManager.creatDisplay();
		scene = new SceneRenderer();
	}
	
	@Override
	public void LoadMap() {
		scene.loadMap();		
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
				SceneRenderer.gamePaused = !SceneRenderer.gamePaused;
				System.out.println(SceneRenderer.gamePaused);
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
