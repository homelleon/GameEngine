package manager.gui.text;

import manager.gui.font.IFontManager;
import object.gui.text.GUIText;
import tool.manager.IManager;

/**
 * Interface to store and control graphic interface texts.
 * 
 * @author homelleon
 * @see GUITextManager
 *
 */

public interface IGUITextManager extends IManager<GUIText> {

	/**
	 * Returns font manager.
	 * 
	 * @return {@link IFontManager} value
	 */
	IFontManager getFonts();

}
