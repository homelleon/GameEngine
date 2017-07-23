package object.gui.text;

import org.lwjgl.util.vector.Vector2f;

public interface GUITextBuilderInterface {
	
	GUITextBuilderInterface setName(String name);
	GUITextBuilderInterface setContent(String text);
	GUITextBuilderInterface setFontName(String fontName);
	GUITextBuilderInterface setFontSize(float fontSize);
	GUITextBuilderInterface setPosition(Vector2f position);
	GUITextBuilderInterface setLineMaxSize(float lineSize);
	GUITextBuilderInterface setCentered(boolean value);
	
	GUIText getText();
}
