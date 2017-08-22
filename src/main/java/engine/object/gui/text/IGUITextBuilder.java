package object.gui.text;

import org.lwjgl.util.vector.Vector2f;

public interface IGUITextBuilder {

	IGUITextBuilder setContent(String text);

	IGUITextBuilder setFontName(String fontName);

	IGUITextBuilder setFontSize(float fontSize);

	IGUITextBuilder setPosition(Vector2f position);

	IGUITextBuilder setLineMaxSize(float lineSize);

	IGUITextBuilder setCentered(boolean value);

	GUIText build(String name);
}
