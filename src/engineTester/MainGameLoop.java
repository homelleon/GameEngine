package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import scene.SceneRenderer;

public class MainGameLoop {

	public static void main(String[] args) {
				
		DisplayManager.creatDisplay();
		SceneRenderer scene = new SceneRenderer();	
		

		while(!Display.isCloseRequested()){
			//game logic
			scene.render();
			DisplayManager.updateDisplay();    
			
		}
		
		scene.cleanUp();
		DisplayManager.closeDisplay();

	}

}
