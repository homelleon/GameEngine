package object.gui.font;

import java.io.File;

import core.settings.EngineSettings;
import object.gui.text.GUIText;
import object.texture.Texture2D;
import primitive.buffer.Loader;

/**
 * Represents a font. It holds the font's texture atlas as well as having the
 * ability to create the quad vertices for any text using this font.
 * 
 * @author Karl
 *
 */
public class FontType {

	private String name;
	private Texture2D textureAtlas;
	private TextMeshCreator meshCreator;

	/**
	 * Creates a new font and loads up the data about each character from the
	 * font file.
	 * 
	 * @param textureAtlas
	 *            - the ID of the font atlas texture.
	 * @param fontFile
	 *            - the font file containing information about each character in
	 *            the texture atlas.
	 */
	public FontType(String name, Texture2D textureAtlas, File fontFile) {
		this.name = name;
		this.textureAtlas = textureAtlas;
		this.meshCreator = new TextMeshCreator(fontFile);
	}

	/**
	 * Creates a new font and loads up the data about each character from the
	 * font file.
	 * 
	 * @param name
	 *            {@link String} value of font name
	 * @param loader
	 *            {@link Loader} value
	 */
	public FontType(String name, Loader loader) {
		new FontType(name, loader.getTextureLoader().loadTexture(EngineSettings.FONT_FILE_PATH, name),
				new File(EngineSettings.FONT_FILE_PATH + name + ".fnt"));
	}

	/**
	 * @return The font texture atlas.
	 */
	public Texture2D getTextureAtlas() {
		return textureAtlas;
	}

	/**
	 * Takes in an unloaded text and calculate all of the vertices for the quads
	 * on which this text will be rendered. The vertex positions and texture
	 * coords and calculated based on the information from the font file.
	 * 
	 * @param text
	 *            - the unloaded text.
	 * @return Information about the vertices of all the quads.
	 */
	public TextMeshData loadText(GUIText text) {
		return meshCreator.createTextMesh(text);
	}

	/**
	 * Gets font name.
	 * 
	 * @return {@link String} value of font name
	 */
	public String getName() {
		return this.name;
	}

}
