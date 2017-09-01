package object.gui.gui.builder;

import java.util.ArrayList;
import java.util.List;

import manager.gui.component.IGUIComponentManager;
import object.gui.gui.GUI;
import object.gui.gui.IGUI;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

/**
 * 
 * @author homelleon
 * @see IGUIBuilder
 */
public class GUIBuilder implements IGUIBuilder {
	
	private IGUIComponentManager componentManager;	
	private List<GUITexture> textures = new ArrayList<GUITexture>();
	private List<GUIText> texts = new ArrayList<GUIText>();

	public GUIBuilder(IGUIComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	@Override
	public IGUIBuilder setTexture(String name, GUITexture texture) {
		this.textures.add(texture.clone(name));
		return this;
	}

	@Override
	public IGUIBuilder setText(String name, GUIText text) {
		this.texts.add(text.clone(name));
		return this;
	}

	@Override
	public IGUI build(String name) {
		componentManager.getTexts().addAll(this.texts);
		componentManager.getTextures().addAll(this.textures);
		return new GUI(name, this.textures, this.texts);
	}

}
