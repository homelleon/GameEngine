package texts;

import java.util.Collection;

import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import fontRendering.TextMaster;
import lights.Light;

/**
 * Interface to store and control graphic interface texts.
 * 
 * @author homelleon
 *
 */

public interface TextManager {
	
	/**
	 * Adds list of text into texts map array.
	 * 
	 * @param textList
	 * 					  {@link Collection}<{@link GuiText}> value of texts
	 * 					  list
	 */
	void addAll(Collection<GuiText> textList);
	
	/**
	 * Adds one text into texts map array.
	 * 
	 * @param text
	 * 				  {@link Light} value
	 */
	void add(GuiText text);
	
	/**
	 * Returns text by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link GuiText} value of chosen text
	 */
	GuiText getByName(String name);
	
	/**
	 * Returns list of texts groupped by name.
	 * 
	 * @return {@link Collection}<{@link GuiText}> value of texts
	 * 		   list
	 */
	Collection<GuiText> getAll();	
	
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

}
