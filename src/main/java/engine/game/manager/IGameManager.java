package game.manager;

import manager.gui.text.IGUITextManager;
import manager.gui.texture.IGUITextureManager;
import object.scene.IScene;

/**
 * 
 * @author homelleon
 * @see GameManager
 */
public interface IGameManager {

	IScene getScene();
	IGUITextureManager getTextures();
	IGUITextManager getTexts();

}
