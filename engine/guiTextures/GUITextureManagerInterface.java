package guiTextures;

import java.util.Collection;

/**
 * Interface to store and control graphic interfaces.
 * 
 * @author homelleon
 *
 */

public interface GUITextureManagerInterface {

	/**
	 * Adds list of graphic interfaces into graphic interfaces map array.
	 * 
	 * @param guiList
	 * 					  {@link Collection}<{@link GUITexture}> value of 
	 * 					  graphic interfaces list
	 */
	void addAll(Collection<GUITexture> guiList);
	
	/**
	 * Adds one light into graphic interfaces map array.
	 * 
	 * @param guiTexture
	 * 				  {@link GUITexture} value
	 */
	void add(GUITexture guiTexture);
	
	/**
	 * Returns graphic interface by name.
	 * 
	 * @param name
	 * 				{@link String} value
	 * 
	 * @return {@link GUITexture} value of chosen graphic interface
	 */
	GUITexture getByName(String name);
	
	/**
	 * Returns list of graphic interfaces groupped by name.
	 * 
	 * @return {@link Collection}<{@link GUITexture}> value of graphic 
	 * 		   interfaces list
	 */
	Collection<GUITexture> getAll();	
	
	/**
	 * Clear all graphic interfaces map and arrays.
	 */
	void clearAll();
}
