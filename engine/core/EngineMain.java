package core;

import core.loop.Loop;
import core.loop.LoopInterface;
import scene.SceneInterface;

/**
 * Main engine crass. Contains main method to create {@link Loop) and start
 * looping the engine events.
 *  
 * @author homelleon
 * @version 1.0
 * 
 */

public class EngineMain {
		
	private static LoopInterface loopGame;
	
	/**
	 * Main method to create Loop object and run it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) { 
		loopGame = new Loop();
		loopGame.run(); 
	}
	
	/**
	 * Method to return {@link SceneInterface} scene parameter to manipulate it out the
	 * engine.
	 *  	
	 * @return Scene object 
	 */
	public static SceneInterface getScene() {
		return loopGame.getScene();
	}
	
	public static void pauseEngine(boolean value) {
		loopGame.setScenePaused(value);
	}
	
	public static boolean getIsEnginePaused() {
		return loopGame.getIsScenePaused();
	}

}
