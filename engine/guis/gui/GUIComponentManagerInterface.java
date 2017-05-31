package guis.gui;

import guis.guiTexts.GUITextManagerInterface;
import guis.guiTextures.GUITexture;
import guis.guiTextures.GUITextureManagerInterface;

/**
 * Manages component of user interface such as Graphic user interface textures
 * and texts.
 * 
 * @author homelleon
 * @see GUIComponentManager
 */
public interface GUIComponentManagerInterface {
	
	/**
	 * Returns {@link GUITextureManagerInterface} that controlls user interface textures 
	 * ({@link GUITexture}).
	 * 
	 * @return {@link GUITextureManagerInterface} value
	 */
	GUITextureManagerInterface getTextures();
	
	/**
	 * Returns {@link GUITextManagerInterface} that controlls user interface texts 
	 * ({@link GuiText})
	 * 
	 * @return {@link GUITextManagerInterface} value
	 */
	GUITextManagerInterface getTexts();
	
	/**
	 * Clean all textures and texts from {@link GUITextureManagerInterface} and {@link GUITextManagerInterface}.  
	 */
	void cleanAll();	
}
