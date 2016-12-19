package engineMain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import scene.ES;
import scene.Scene;
import scene.SceneGame;

public class LoopEditor implements Loop { 
	
	private static final String SETTINGS_NAME = "settings";
	
	private Scene scene; 
	
	public LoopEditor() { 
		DisplayManager.creatDisplay(ES.DISPLAY_EDIT_MODE);
		scene = new SceneGame();		
	}

	@Override
	public void loadMap(String name) {
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
	
	public Scene getScene() {
		return scene;
	}

	@Override
	public void loadGameSettings() {
		SettingsLoader setLoader = new SettingsTXTLoader();  
		GameSettings settings = setLoader.loadSettings(SETTINGS_NAME);
		System.out.println(settings.getMapName());
		scene.loadMap(settings.getMapName());		
	}
	
}
