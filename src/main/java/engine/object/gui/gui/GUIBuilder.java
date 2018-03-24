package object.gui.gui;

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
		this.textures.add(texture);
		return this;
	}
	
	public GUIBuilder setText(String name, GUIText text) {
		this.texts.add(text);
		return this;
	}

	public GUI build(String name) {
		componentManager.getTexts().addAll(this.texts);
		componentManager.getTextures().addAll(this.textures);
		return new GUI(name, this.textures, this.texts);
	}

}
