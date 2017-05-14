package userInterfaces;

import fontMeshCreator.GuiText;
import guis.GUIManager;
import guis.GuiTexture;
import texts.GUITextManager;

/**
 * Manages component of user interface such as Graphic user interface textures
 * and texts.
 * 
 * @author homelleon
 *
 */
public interface UIComponentManager {
	
	/**
	 * Returns {@link GUIManager} that controlls user interface textures 
	 * ({@link GuiTexture}).
	 * 
	 * @return {@link GUIManager} value
	 */
	GUIManager getTextures();
	
	/**
	 * Returns {@link GUITextManager} that controlls user interface texts 
	 * ({@link GuiText})
	 * 
	 * @return {@link GUITextManager} value
	 */
	GUITextManager getTexts();
	
	/**
	 * Clean all textures and texts from {@link GUIManager} and {@link GUITextManager}.  
	 */
	void cleanAll();	
}
