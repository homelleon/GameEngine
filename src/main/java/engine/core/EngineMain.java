package core;

import core.loop.Loop;
import core.loop.ILoop;
import object.scene.scene.IScene;

/**
 * Main engine class. Contains main method to create {@link Loop} and start
 * looping the engine events.
 * 
 * @author homelleon
 * @version 1.0
 * 
 */

public class EngineMain {

	private static ILoop loopGame;

	/**
	 * Main method to create Loop object and run it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		loopGame = Loop.getInstance();
		loopGame.run();
	}

	/**
	 * Method to return {@link IScene} scene parameter to manipulate it
	 * out the engine.
	 * 
	 * @return Scene object
	 */
	public static IScene getScene() {
		return loopGame.getScene();
	}

	public static void pauseEngine(boolean value) {
		loopGame.setScenePaused(value);
	}

	public static boolean getIsEnginePaused() {
		return loopGame.getIsScenePaused();
	}

}
