package engineMain;

import scene.Scene;

/**
 * Main engine crass. Contains main method to create {@link Loop) and start
 * looping the engine events.
 *  
 * @author homelleon
 * @version 1.0
 * 
 */

public class EngineMain {
		
	private static Loop loopGame;
	
	/**
	 * Main method to create Loop object and run it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) { 
		loopGame = new LoopGame();
		loopGame.run();  
	}
	
	/**
	 * Method to return {@link Scene} scene parameter to manipulate it out the
	 * engine.
	 *  	
	 * @return Scene object 
	 */
	public static Scene getScene() {
		return loopGame.getScene();
	}

}
