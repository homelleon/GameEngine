package object.gui.text;

import org.lwjgl.util.vector.Vector2f;

public class GUITextBuilder implements IGUITextBuilder {

	private String name;
	private String text;
	private String fontName;
	private float fontSize;
	private Vector2f position;
	private float lineSize;
	private boolean isCentered;

	@Override
	public IGUITextBuilder setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public IGUITextBuilder setContent(String text) {
		this.text = text;
		return this;
	}

	@Override
	public IGUITextBuilder setFontName(String fontName) {
		this.fontName = fontName;
		return this;
	}

	@Override
	public IGUITextBuilder setFontSize(float fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	@Override
	public IGUITextBuilder setPosition(Vector2f position) {
		this.position = position;
		return this;
	}

	@Override
	public IGUITextBuilder setLineMaxSize(float lineSize) {
		this.lineSize = lineSize;
		return this;
	}

	@Override
	public IGUITextBuilder setCentered(boolean value) {
		this.isCentered = value;
		return this;
	}

	@Override
	public GUIText getText() {
		return new GUIText(this.name, text, fontSize, fontName, position, lineSize, isCentered);
	}

}
