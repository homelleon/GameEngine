package object.gui.gui.builder;

import java.util.ArrayList;
import java.util.List;

import object.gui.gui.GUI;
import object.gui.gui.IGUI;
import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

public class GUIBuilder implements IGUIBuilder {
	
	private List<GUITexture> textures = new ArrayList<GUITexture>();
	private List<GUIText> texts = new ArrayList<GUIText>();

	@Override
	public IGUIBuilder setTexture(GUITexture texture) {
		this.textures.add(texture);
		return this;
	}

	@Override
	public IGUIBuilder setText(GUIText text) {
		this.texts.add(text);
		return this;
	}

	@Override
	public IGUI build(String name) {
		return new GUI(name, this.textures, this.texts);
	}

}
