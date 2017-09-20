package object.gui.texture;

import tool.math.vector.Vec2f;

public interface IGUITextureBuilder {

	IGUITextureBuilder setTextureName(String textureName);

	IGUITextureBuilder setPosition(Vec2f position);

	IGUITextureBuilder setScale(Vec2f scale);

	GUITexture build(String name);

}
