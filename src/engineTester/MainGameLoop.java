package engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import scene.SceneRenderer;

public class MainGameLoop {

	public static void main(String[] args) {
				
		DisplayManager.creatDisplay();
		SceneRenderer scene = new SceneRenderer();	
		

		while(!Display.isCloseRequested()){
			scene.render();
			DisplayManager.updateDisplay(); 
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				break;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_P)){
				SceneRenderer.gamePaused = !SceneRenderer.gamePaused;
			}
			
		}
		
		scene.cleanUp();
		DisplayManager.closeDisplay();

	}

}
