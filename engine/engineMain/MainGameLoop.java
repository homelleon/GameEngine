package engineMain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import maps.GameMap;
import scene.SceneGame;
import scene.Scene;

public class MainGameLoop implements Engine {
	
	private static final String SETTINGS_NAME = "settings";
	
	private Scene scene;
	private boolean isScenePaused = false; 
	
	public MainGameLoop() {
		DisplayManager.creatDisplay();
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

	@Override
	public void loadGameSettings() {
		SettingsLoader setLoader = new SettingsTXTLoader();  
		GameSettings settings = setLoader.loadSettings(SETTINGS_NAME);
		scene.loadMap(settings.getMapName());			
	}



}
