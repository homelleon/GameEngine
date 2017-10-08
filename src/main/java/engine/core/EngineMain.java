package core;

import core.loop.Loop;
import core.settings.EngineSettings;
import object.scene.IScene;
import core.loop.ILoop;

/**
 * Main engine class. Contains main method to create {@link Loop} and run
 * the engine events.
 * 
 * @author homelleon
 * @version 1.0
 * 
 */
public class EngineMain {
	
	private static int wiredFrameMode = EngineSettings.WIRED_FRAME_NONE;

	private static ILoop loopGame;

	/**
	 * <b>Main method</b>
	 * <br>Starts game engine.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		loopGame = Loop.getInstance();
		loopGame.run();
	}
	
	/**
	 * Intialize stopping the engine.
	 */
	public static void exit() {
		loopGame.exit();
	}

	/**
	 * Returns {@link IScene} scene.
	 * 
	 * @return Scene object
	 */
	public static IScene getScene() {
		return loopGame.getScene();
	}
	
	/**
	 * Switches game engine pause mode.
	 * 
	 * @param value boolean
	 */
	public static void pauseEngine(boolean value) {
		loopGame.setScenePaused(value);
	}

	/**
	 * Gets if engine is in pause mode.
	 * 
	 * @return <i>true</i> if engine is on pause<br>
	 * 		   <i>false</i> if engine is not on pause
	 */
	public static boolean getIsEnginePaused() {
		return loopGame.getIsScenePaused();
	}
	
	public static int getWiredFrameMode() {
		return wiredFrameMode;
	}
	
	public static void switchWiredFrameMode() {
		if(wiredFrameMode == EngineSettings.WIRED_FRAME_ENTITY_TERRAIN) {
			wiredFrameMode = EngineSettings.WIRED_FRAME_NONE;
		} else {
			wiredFrameMode++;
		}
	}

}
