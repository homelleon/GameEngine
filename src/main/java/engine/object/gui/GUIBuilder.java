package object.gui;

import java.util.ArrayList;
import java.util.List;

import manager.gui.GUIComponentManager;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

/**
 * 
 * @author homelleon
 * @see IGUIBuilder
 */
public class GUIBuilder {
	
	private GUIComponentManager componentManager;	
	private List<GUITexture> textures = new ArrayList<GUITexture>();
	private List<GUIText> texts = new ArrayList<GUIText>();

	public GUIBuilder(GUIComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	public GUIBuilder setTexture(String name, GUITexture texture) {
		textures.add(texture);
		return this;
	}
	
	public GUIBuilder setText(String name, GUIText text) {
		texts.add(text);
		return this;
	}

	public GUI build(String name) {
		componentManager.getTexts().addAll(texts);
		componentManager.getTextures().addAll(textures);
		return new GUI(name, textures, texts);
	}

}
