package manager.gui.text;

import manager.gui.font.IFontManager;
import object.gui.text.GUIText;
import tool.manager.IManager;

/**
 * Provide access to storing and controling graphic user interface texts.
 * 
 * @author homelleon
 * @see GUITextManager
 * @see GUIText
 */

public interface IGUITextManager extends IManager<GUIText> {

	/**
	 * Change content text of GUIText object and rebuild it.
	 * 
	 * @param name String value of GUIText name
	 * @param content String value of text content 
	 */
	void changeContent(String name, String content);
	
	/**
	 * Returns font manager.
	 * 
	 * @return {@link IFontManager} value
	 */
	IFontManager getFonts();

}
