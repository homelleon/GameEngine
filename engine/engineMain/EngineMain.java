package engineMain;

import scene.Scene;

public class EngineMain {
	
	/*
	 *  EngineMain - главный класс игры	   
	 *  03.02.17
	 *  -----------------
	 */
		
	private static Loop loopGame;
	
	//главная процедура запуска приложения
	public static void main(String[] args) { 
		loopGame = new LoopGame();
		loopGame.run();  
	}
	
	//вернуть игровую сцену
	public static Scene getScene() {
		return loopGame.getScene();
	}

}
