package object.gui.text.manager;

import java.util.Collection;

import org.w3c.dom.Document;

import object.gui.font.manager.FontManagerInterface;
import object.gui.text.GUIText;
import object.light.Light;
import tool.manager.ManagerInterface;

/**
 * Interface to store and control graphic interface texts.
 * 
 * @author homelleon
 * @see GUITextManager
 *
 */

public interface GUITextManagerInterface extends ManagerInterface<GUIText> {

	/**
	 * Returns font manager.
	 * 
	 * @return {@link FontManagerInterface} value
	 */
	FontManagerInterface getFonts();

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
