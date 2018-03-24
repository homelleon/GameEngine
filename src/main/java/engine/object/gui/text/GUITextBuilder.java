package object.gui.text;

import tool.math.vector.Vector2f;

public class GUITextBuilder {
	
	private String text;
	private String fontName;
	private float fontSize;
	private Vector2f position;
	private float lineSize;
	private boolean isCentered;

	public GUITextBuilder setContent(String text) {
		this.text = text;
		return this;
	}

	public GUITextBuilder setFontName(String fontName) {
		this.fontName = fontName;
		return this;
	}

	public GUITextBuilder setFontSize(float fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	public GUITextBuilder setPosition(Vector2f position) {
		this.position = position;
		return this;
	}

	public GUITextBuilder setLineMaxSize(float lineSize) {
		this.lineSize = lineSize;
		return this;
	}

	public GUITextBuilder setCentered(boolean value) {
		this.isCentered = value;
		return this;
	}

	public GUIText build(String name) {
		return new GUIText(name, text, fontSize, fontName, position, lineSize, isCentered);
	}

}
