package object.gui.texture;

import tool.math.vector.Vector2f;

public interface IGUITextureBuilder {

	IGUITextureBuilder setTextureName(String textureName);

	IGUITextureBuilder setPosition(Vector2f position);

	IGUITextureBuilder setScale(Vector2f scale);

	GUITexture build(String name);

}
