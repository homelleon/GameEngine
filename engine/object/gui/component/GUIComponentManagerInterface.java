package object.gui.component;

import java.util.Collection;

import object.gui.group.GUIGroupInterface;
import object.gui.text.manager.GUITextManagerInterface;
import object.gui.texture.GUITexture;
import object.gui.texture.manager.GUITextureManagerInterface;

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
	 * Renders graphic user interface.
	 */
	void render(Collection<GUIGroupInterface> groups);
	
	/**
	 * Clean all textures and texts from {@link GUITextureManagerInterface} and {@link GUITextManagerInterface}.  
	 */
	void cleanAll();	
}
