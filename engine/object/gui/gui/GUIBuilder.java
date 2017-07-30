package object.gui.gui;

import java.util.ArrayList;
import java.util.List;

import object.gui.text.GUIText;
import object.gui.texture.GUITexture;

public class GUIBuilder implements GUIBuilderInterface {
	
	private List<GUITexture> textures = new ArrayList<GUITexture>();
	private List<GUIText> texts = new ArrayList<GUIText>();

	@Override
	public GUIBuilderInterface setTexture(GUITexture texture) {
		this.textures.add(texture);
		return this;
	}

	@Override
	public GUIBuilderInterface setText(GUIText text) {
		this.texts.add(text);
		return this;
	}

	@Override
	public GUIInterface getGUI(String name) {
		return new GUI(name, this.textures, this.texts);
	}

}
