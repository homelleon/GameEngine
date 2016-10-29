package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import scene.SceneRenderer;
import scene.WorldGethable;

public class MainGameLoop implements Engine {
	
	private WorldGethable scene;

	
	@Override
	public void run() {
		DisplayManager.creatDisplay();
		this.scene = new SceneRenderer(); 
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
			
			if(Keyboard.isKeyDown(Keyboard.KEY_T)) {
				
			}
		}
		
		scene.cleanUp();
		DisplayManager.closeDisplay();
		
	}

	@Override
	public void exit() {
		Display.destroy();		
	}

}
