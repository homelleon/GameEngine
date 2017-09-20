package object.gui.text;

import tool.math.vector.Vec2f;

public interface IGUITextBuilder {

	IGUITextBuilder setContent(String text);

	IGUITextBuilder setFontName(String fontName);

	IGUITextBuilder setFontSize(float fontSize);

	IGUITextBuilder setPosition(Vec2f position);

	IGUITextBuilder setLineMaxSize(float lineSize);

	IGUITextBuilder setCentered(boolean value);

	GUIText build(String name);
}
