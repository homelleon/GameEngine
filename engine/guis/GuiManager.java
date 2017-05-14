package guis;

import java.util.Collection;

/**
 * Interface to store and control graphic interfaces.
 * 
 * @author homelleon
 *
 */

public interface GUIManager {

	/**
	 * Adds list of graphic interfaces into graphic interfaces map array.
	 * 
	 * @param guiList
	 * 					  {@link Collection}<{@link GuiTexture}> value of 
	 * 					  graphic interfaces list
	 */
	void addAll(Collection<GuiTexture> guiList);
	
	/**
	 * Adds one light into graphic interfaces map array.
	 * 
	 * @param gui
	 * 				  {@link GuiTexture} value
	 */
	void add(GuiTexture gui);
	
	/**
	 * Returns graphic interface by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link GuiTexture} value of chosen graphic interface
	 */
	GuiTexture getByName(String name);
	
	/**
	 * Returns list of graphic interfaces groupped by name.
	 * 
	 * @return {@link Collection}<{@link GuiTexture}> value of graphic 
	 * 		   interfaces list
	 */
	Collection<GuiTexture> getAll();	
	
	/**
	 * Clear all graphic interfaces map and arrays.
	 */
	void clearAll();
}
