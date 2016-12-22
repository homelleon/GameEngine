package engineMain;

import scene.Scene;

public class EngineMain {
		
	private static Loop loopGame;

	public static void main(String[] args) { 
		loopGame = new LoopGame();
		loopGame.run();  
	}
	
	
	public static Scene getScene() {
		return loopGame.getScene();
	}

}
