package engineMain;

import scene.SceneO;

public class EngineMain {
		
	private static Loop engine;

	public static void main(String[] args) { 
		engine = new GameLoop();
		engine.loadGameSettings();
		engine.init(); 
		engine.run();  
	}
	
	
	public static SceneO getScene() {
		return engine.getScene();
	}

}
