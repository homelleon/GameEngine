package object.gui.font.manager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import core.settings.EngineSettings;
import object.gui.font.FontType;
import renderer.Loader;

public class FontManager implements FontManagerInterface {
	
	private Loader loader;
	private Map<String, FontType> fonts = new HashMap<String, FontType>();
	
	public FontManager(Loader loader) {
		this.loader = loader;
		this.create("candara");
	}

	@Override
	public void create(String fontName) {
		FontType font = new FontType(fontName, this.loader.loadTexture(EngineSettings.FONT_FILE_PATH, fontName),
				new File(EngineSettings.FONT_FILE_PATH + fontName + ".fnt"));
		this.fonts.put(font.getName(), font);
	}
	
	@Override
	public void add(FontType font) {		
		this.fonts.put(font.getName(), font);		
	}

	@Override
	public FontType get(String name) {
		return this.fonts.get(name);		
	}

	@Override
	public void remove(String name) {
		this.fonts.remove(name);		
	}

	@Override
	public void cleanUp() {
		this.fonts.clear();
	}	

}
