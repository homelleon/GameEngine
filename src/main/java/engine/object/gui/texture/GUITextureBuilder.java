package object.gui.texture;

import core.settings.EngineSettings;
import primitive.buffer.Loader;
import primitive.texture.Texture2D;
import tool.math.vector.Vector2f;

public class GUITextureBuilder {

	private String textureName;
	private Vector2f position;
	private Vector2f scale;

	public GUITextureBuilder setTextureName(String textureName) {
		this.textureName = textureName;
		return this;
	}

	public GUITextureBuilder setPosition(Vector2f position) {
		this.position = position;
		return this;
	}

	public GUITextureBuilder setScale(Vector2f scale) {
		this.scale = scale;
		return this;
	}

	public GUITexture build(String name) {
		Loader loader = Loader.getInstance();
		Texture2D texture = loader.getTextureLoader().loadGUITexture(EngineSettings.TEXTURE_INTERFACE_PATH, this.textureName);
		return new GUITexture(name, texture, this.position, this.scale);
	}

}
