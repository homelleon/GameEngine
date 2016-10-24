package engine.engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import engine.Engine;
import engine.renderEngine.DisplayManager;
import engine.scene.SceneRenderer;

public class MainGameLoop implements Engine {

	@Override
	public void run() {
		DisplayManager.creatDisplay();
		SceneRenderer scene = new SceneRenderer();	
		
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

	@Override
	public void exit() {
		Display.destroy();		
	}

}
