package engineMain;

import scene.Scene;

public class Main {
		
	private static Engine engine;

	public static void main(String[] args) { 
		engine = new MainGameLoop();
		engine.loadGameSettings();
		engine.init(); 
		engine.run();  
	}
	
	
	public static Scene getScene() {
		return engine.getScene();
	}

}
