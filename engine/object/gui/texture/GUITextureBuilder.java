package object.gui.texture;

import org.lwjgl.util.vector.Vector2f;

import core.settings.EngineSettings;
import renderer.loader.Loader;

public class GUITextureBuilder implements GUITextureBuilderInterface {

	private String name;
	private String textureName;
	private Vector2f position;
	private Vector2f scale;

	@Override
	public GUITextureBuilderInterface setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public GUITextureBuilderInterface setTextureName(String textureName) {
		this.textureName = textureName;
		return this;
	}

	@Override
	public GUITextureBuilderInterface setPosition(Vector2f position) {
		this.position = position;
		return this;
	}

	@Override
	public GUITextureBuilderInterface setScale(Vector2f scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public GUITexture getTexture() {
		Loader loader = Loader.getInstance();
		int texture = loader.loadTexture(EngineSettings.TEXTURE_INTERFACE_PATH, this.textureName);
		return new GUITexture(this.name, texture, this.position, this.scale);
	}

}
