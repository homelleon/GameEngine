package object.gui.text;

import java.util.Collection;

import object.gui.font.GUIText;
import object.gui.font.TextMaster;
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
	 * 					  {@link Collection}<{@link GUIText}> value of texts
	 * 					  list
	 */
	void addAll(Collection<GUIText> textList);
	
	/**
	 * Adds one text into texts map array.
	 * 
	 * @param text
	 * 				  {@link Light} value
	 */
	void add(GUIText text);
	
	/**
	 * Returns text by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link GUIText} value of chosen text
	 */
	GUIText getByName(String name);
	
	/**
	 * Returns list of texts groupped by name.
	 * 
	 * @return {@link Collection}<{@link GUIText}> value of texts
	 * 		   list
	 */
	Collection<GUIText> getAll();	
	
	/**
	 * Returns text master.
	 * 
	 * @return {@link TextMaster} value
	 */
	TextMaster getMaster();
	
	/**
	 * Clear all texts map and arrays.
	 */
	void clearAll();
	
	/**
	 * Reads interface texture entities from file.
	 * 
	 * @param fileName {@link String} value of file name
	 */
	void readFile(String fileName);

}
