package object.gui.texture;

import org.lwjgl.util.vector.Vector2f;

public interface GUITextureBuilderInterface {

	GUITextureBuilderInterface setName(String name);

	GUITextureBuilderInterface setTextureName(String textureName);

	GUITextureBuilderInterface setPosition(Vector2f position);

	GUITextureBuilderInterface setScale(Vector2f scale);

	GUITexture getTexture();

}
