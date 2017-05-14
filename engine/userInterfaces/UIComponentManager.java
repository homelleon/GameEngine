package userInterfaces;

import fontMeshCreator.GuiText;
import guis.GuiManager;
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
	 * Returns {@link GuiManager} that controlls user interface textures 
	 * ({@link GuiTexture}).
	 * 
	 * @return {@link GuiManager} value
	 */
	GuiManager getTextures();
	
	/**
	 * Returns {@link GUITextManager} that controlls user interface texts 
	 * ({@link GuiText})
	 * 
	 * @return {@link GUITextManager} value
	 */
	GUITextManager getTexts();
	
	/**
	 * Clean all textures and texts from {@link GuiManager} and {@link GUITextManager}.  
	 */
	void cleanAll();	
}
