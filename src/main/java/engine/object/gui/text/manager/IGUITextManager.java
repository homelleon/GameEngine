package object.gui.text.manager;

import org.w3c.dom.Document;

import object.gui.font.manager.IFontManager;
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

	/**
	 * Reads interface texture entities from file.
	 * 
	 * @param fileName
	 *            {@link String} value of file name
	 */
	void readFile(String fileName);
	
	/**
	 * Reads interface texture entities from xml structured document.
	 * 
	 * @param document
	 */
	void readDocument(Document document);

}
