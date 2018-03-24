package manager.gui;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import core.settings.EngineSettings;
import object.gui.font.FontType;
import primitive.buffer.Loader;

/**
 * Manages GUI fonts.
 * 
 * @author homelleon
 *
 */
public class FontManager {

	private Map<String, FontType> fonts = new HashMap<String, FontType>();

	public FontType create(String name) {
		FontType font;
		if (fonts.containsKey(name)) {
			font = fonts.get(name);
		} else {
			Loader loader = Loader.getInstance();
			InputStreamReader fontStream = new InputStreamReader(Class.class.getResourceAsStream(EngineSettings.FONT_FILE_PATH + name + ".fnt"));
			font = new FontType(name, loader.getTextureLoader().loadTexture(EngineSettings.FONT_FILE_PATH, name),
					fontStream);
			fonts.put(font.getName(), font);
		}
		return font;
	}

	public FontType get(String name) {
		return fonts.containsKey(name) ? fonts.get(name) : null;
	}

	public boolean delete(String name) {
		if (fonts.containsKey(name)) {
			fonts.remove(name);
			return true;
		} else {
			return false;
		}
	}

	public void clean() {
		fonts.clear();
	}

}
