package userInterfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fontMeshCreator.GuiText;
import guis.GuiTexture;

public class UIBase {
	
	protected Map<String, GuiText> texts = new HashMap<String, GuiText>();
	protected Map<String, GuiTexture> guis = new HashMap<String, GuiTexture>();

	public void addText(GuiText text) {
		this.texts.put(text.getName(), text);
	}

	public GuiText getText(String name) {
		return this.texts.get(name);
	}

	public Collection<GuiText> getAllTexts() {
		return this.texts.values();
	}

	public void addTexture(GuiTexture texture) {
		this.guis.put(texture.getName(), texture);
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
