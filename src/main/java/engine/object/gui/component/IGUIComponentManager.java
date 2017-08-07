package object.gui.component;

import java.util.Collection;

import object.gui.group.IGUIGroup;
import object.gui.text.manager.IGUITextManager;
import object.gui.texture.GUITexture;
import object.gui.texture.manager.IGUITextureManager;

/**
 * Manages component of user interface such as Graphic user interface textures
 * and texts.
 * 
 * @author homelleon
 * @see GUIComponentManager
 */
public interface IGUIComponentManager {

	/**
	 * Returns {@link IGUITextureManager} that controlls user interface
	 * textures ({@link GUITexture}).
	 * 
	 * @return {@link IGUITextureManager} value
	 */
	IGUITextureManager getTextures();

	/**
	 * Returns {@link IGUITextManager} that controlls user interface
	 * texts ({@link GuiText})
	 * 
	 * @return {@link IGUITextManager} value
	 */
	IGUITextManager getTexts();

	/**
	 * Renders graphic user interface.
	 */
	void render(Collection<IGUIGroup> groups);

	/**
	 * Clean all textures and texts from {@link IGUITextureManager} and
	 * {@link IGUITextManager}.
	 */
	void cleanAll();
}
