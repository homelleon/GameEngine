package object.gui.texture;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import core.settings.EngineSettings;
import renderer.loader.Loader;

public class GUITextureBuilder implements IGUITextureBuilder {

	private String name;
	private String textureName;
	private Vector2f position;
	private Vector2f scale;

	@Override
	public IGUITextureBuilder setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public IGUITextureBuilder setTextureName(String textureName) {
		this.textureName = textureName;
		return this;
	}

	@Override
	public IGUITextureBuilder setPosition(Vector2f position) {
		this.position = position;
		return this;
	}

	@Override
	public IGUITextureBuilder setScale(Vector2f scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public GUITexture getTexture() {
		Loader loader = Loader.getInstance();
		Texture texture = loader.getTextureLoader().loadGUITexture(EngineSettings.TEXTURE_INTERFACE_PATH, this.textureName);
		return new GUITexture(this.name, texture, this.position, this.scale);
	}

}
