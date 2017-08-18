package manager.gui.font;

import object.gui.font.FontType;

/**
 * Implement access into fonts array.
 * 
 * @author homelleon
 * @see FontManager
 */
public interface IFontManager {

	/**
	 * Creates font with name and returns it as FontType object. <br>
	 * Search font only in fonts folder that is chosen in engine settings. <br>
	 * Doesn't create new one if the font with that name is extist. Returns old
	 * one with the same name in that case.
	 * 
	 * @param name
	 *            {@link String} value of font name
	 * @return {@link FontType} value of created font object
	 */
	public FontType create(String name);

	/**
	 * Returns font with name if extist; <br>
	 * If not extist returns null.
	 * 
	 * @param name
	 *            {@link String} value of font name
	 * @return {@link FontType} value of font object
	 */
	public FontType get(String name);

	/**
	 * Deletes font object with chosen name. <br>
	 * Returns true or false depending on if that font was extisted.
	 * 
	 * @param name
	 *            {@link String} value of font name
	 * @return true if font was extisted <br>
	 * 		false if font wasn't extisted
	 */
	public boolean delete(String name);

	/**
	 * Clean fonts array deleting all fonts.
	 */
	public void cleanUp();

}
