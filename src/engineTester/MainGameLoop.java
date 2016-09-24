package engineTester;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import scene.sceneRenderer;

public class MainGameLoop {

	public static void main(String[] args) {
				
		DisplayManager.creatDisplay();
		sceneRenderer scene = new sceneRenderer();	
		

		while(!Display.isCloseRequested()){
			//game logic
			scene.render();
			DisplayManager.updateDisplay();    
			
		}
		
		scene.cleanUp();
		DisplayManager.closeDisplay();

	}

}
