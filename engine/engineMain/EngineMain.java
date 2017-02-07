package engineMain;

import scene.Scene;

public class EngineMain {
	
	/*
	 *  EngineMain - ������� ����� ����	   
	 *  03.02.17
	 *  -----------------
	 */
		
	private static Loop loopGame;
	
	public static void main(String[] args) { 
		loopGame = new LoopGame();
		loopGame.run();  
	}
	
	public static Scene getScene() {
		return loopGame.getScene();
	}

}
