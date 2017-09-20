package object.gui.text;

import tool.math.vector.Vec2f;

public class GUITextBuilder implements IGUITextBuilder {
	
	private String text;
	private String fontName;
	private float fontSize;
	private Vec2f position;
	private float lineSize;
	private boolean isCentered;

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
	public IGUITextBuilder setPosition(Vec2f position) {
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
	public GUIText build(String name) {
		return new GUIText(name, text, fontSize, fontName, position, lineSize, isCentered);
	}

}
