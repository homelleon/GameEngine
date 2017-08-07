package object.gui.font.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import core.settings.EngineSettings;
import object.gui.font.FontType;
import renderer.loader.Loader;

public class FontManager implements IFontManager {

	private Map<String, FontType> fonts = new HashMap<String, FontType>();

	@Override
	public FontType create(String name) {
		FontType font = null;
		if (fonts.containsKey(name)) {
			font = fonts.get(name);
		} else {
			Loader loader = Loader.getInstance();
			font = new FontType(name, loader.getTextureLoader().loadTexture(EngineSettings.FONT_FILE_PATH, name),
					new File(EngineSettings.FONT_FILE_PATH + name + ".fnt"));
			fonts.put(font.getName(), font);
		}
		return font;
	}

	@Override
	public FontType get(String name) {
		FontType font = null;
		if (fonts.containsKey(name)) {
			font = fonts.get(name);
		}
		return font;
	}

	@Override
	public boolean delete(String name) {
		boolean wasExisted = false;
		if (fonts.containsKey(name)) {
			wasExisted = true;
			fonts.remove(name);
		}
		return wasExisted;
	}

	@Override
	public void cleanUp() {
		fonts.clear();
	}

}
