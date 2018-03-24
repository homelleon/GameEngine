package core.game;

import manager.gui.GUITextManager;
import manager.gui.GUITextureManager;
import scene.Scene;

/**
 * 
 * @author homelleon
 * @see GameManager
 */
public interface IGameManager {

	/**
	 * Gets engine scene object.
	 * 
	 * @return {@link IScene} object
	 */
	Scene getScene();
	
	/**
	 * Gets texture manager.
	 * 
	 * @return {@link IGUITextureManager} object
	 */
	GUITextureManager getTextures();
	
	/**
	 * Gets text manager.
	 * 
	 * @return {@link IGUITextManager} object
	 */
	GUITextManager getTexts();

}
