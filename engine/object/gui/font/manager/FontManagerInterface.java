package object.gui.font.manager;

import object.gui.font.FontType;

/**
 * Manages and strores text font by name.
 * 
 * @author homelleon
 * @see FontManager
 */
public interface FontManagerInterface {
	
	/**
	 * Creates new font by its name.
	 * 
	 * @param fontName {@link String} value of font name
	 */
	public void create(String fontName);
	
	/**
	 * Adds font into font map.
	 * 
	 * @param font {@link FontType} font object
	 */
	public void add(FontType font);
	
	/**
	 * Gets font by its name.
	 * 
	 * @param name {@link String} valuf of font name
	 */
	public FontType get(String name);
	
	/**
	 * Removes font by its name.
	 * 
	 * @param name {@link String} value of font name
	 */
	public void remove(String name);
	
	/**
	 * Cleans fonts array from all fonts.
	 */
	public void cleanUp();

}
