package object.gui.text.manager;

import java.util.Collection;

import org.w3c.dom.Document;

import object.gui.font.manager.FontManagerInterface;
import object.gui.text.GUIText;
import object.light.Light;

/**
 * Interface to store and control graphic interface texts.
 * 
 * @author homelleon
 * @see GUITextManager
 *
 */

public interface GUITextManagerInterface {

	/**
	 * Adds list of text into texts map array.
	 * 
	 * @param textList
	 *            {@link Collection}<{@link GUIText}> value of texts list
	 */
	void addAll(Collection<GUIText> textList);

	/**
	 * Adds one text into texts map array.
	 * 
	 * @param text
	 *            {@link Light} value
	 */
	void add(GUIText text);

	/**
	 * Gets text by name.
	 * 
	 * @param name
	 *            {@link String} value
	 * 
	 * @return {@link GUIText} value of chosen text
	 */
	GUIText get(String name);

	/**
	 * Returns list of texts groupped by name.
	 * 
	 * @return {@link Collection}<{@link GUIText}> value of texts list
	 */
	Collection<GUIText> getAll();

	/**
	 * Removes text by name.
	 * 
	 * @param name
	 *            {@link String name}
	 */
	public void remove(String name);

	/**
	 * Returns font manager.
	 * 
	 * @return {@link FontManagerInterface} value
	 */
	FontManagerInterface getFonts();

	/**
	 * Clear all texts map and arrays.
	 */
	void cleanUp();

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
