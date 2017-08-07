package object.gui.texture;

import org.lwjgl.util.vector.Vector2f;

public interface IGUITextureBuilder {

	IGUITextureBuilder setName(String name);

	IGUITextureBuilder setTextureName(String textureName);

	IGUITextureBuilder setPosition(Vector2f position);

	IGUITextureBuilder setScale(Vector2f scale);

	GUITexture getTexture();

}