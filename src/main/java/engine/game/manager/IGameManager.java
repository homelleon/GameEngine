package game.manager;

import manager.gui.text.IGUITextManager;
import manager.gui.texture.IGUITextureManager;
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
	IGUITextureManager getTextures();
	
	/**
	 * Gets text manager.
	 * 
	 * @return {@link IGUITextManager} object
	 */
	IGUITextManager getTexts();

}
