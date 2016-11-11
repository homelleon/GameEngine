package engineMain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import maps.GameMap;
import renderEngine.DisplayManager;
import scene.EditorSceneRenderer;
import scene.GameSceneRenderer;
import scene.WorldGethable;

public class MainEditorLoop implements Engine {
	
	private WorldGethable scene;
	private static final String SETTINGS_NAME = "Settings";
	
	public MainEditorLoop() {
		DisplayManager.creatDisplay();
		scene = new EditorSceneRenderer();		
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

	@Override
	public GameMap getMap() {
		return scene.getMap();
	}

	@Override
	public void loadGameSettings() {
		SettingsLoader setLoader = new SettingsTXTLoader();  
		GameSettings settings = setLoader.loadSettings(SETTINGS_NAME);
		scene.loadMap(settings.getMapName());		
	}

}
