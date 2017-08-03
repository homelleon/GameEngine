package object.gui.texture.manager;

import java.util.Collection;

import org.w3c.dom.Document;

import object.gui.texture.GUITexture;

/**
 * Interface to store and control graphic interfaces.
 * 
 * @author homelleon
 * @see GUITextureManager
 */

public interface GUITextureManagerInterface {

	/**
	 * Adds list of graphic interfaces into graphic interfaces map array.
	 * 
	 * @param guiList
	 *            {@link Collection}<{@link GUITexture}> value of graphic
	 *            interfaces list
	 */
	void addAll(Collection<GUITexture> guiList);

	/**
	 * Adds one light into graphic interfaces map array.
	 * 
	 * @param guiTexture
	 *            {@link GUITexture} value
	 */
	void add(GUITexture guiTexture);

	/**
	 * Returns graphic interface by name.
	 * 
	 * @param name
	 *            {@link String} value
	 * 
	 * @return {@link GUITexture} value of chosen graphic interface
	 */
	GUITexture get(String name);

	/**
	 * Returns list of graphic interfaces groupped by name.
	 * 
	 * @return {@link Collection}<{@link GUITexture}> value of graphic
	 *         interfaces list
	 */
	Collection<GUITexture> getAll();

	/**
	 * Clear all graphic interfaces map and arrays.
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