package userInterfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import guis.GuiTexture;

public class UIBase {
	
	protected Map<String, GuiText> texts = new HashMap<String, GuiText>();
	protected Map<String, GuiTexture> guis = new HashMap<String, GuiTexture>();

	public void addText(String name, GuiText text, FontType font, Vector2f position) {
		this.texts.put(text.getName(), text);
	}

	public GuiText getText(String name) {
		return this.texts.get(name);
	}

	public Collection<GuiText> getAllTexts() {
		return this.texts.values();
	}

	public void addTexture(String name, GuiTexture texture, Vector2f position) {
		this.guis.put(name, texture);
	}

	public GuiTexture getTexture(String name) {
		return this.guis.get(name);
	}

	public Collection<GuiTexture> getAllTextures() {
		return this.guis.values();
	}
	
	public void cleanUp() {
		texts.clear();
		guis.clear();
	}

}
