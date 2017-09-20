package object.gui.texture;

import org.newdawn.slick.opengl.Texture;

import core.settings.EngineSettings;
import renderer.loader.Loader;
import tool.math.vector.Vec2f;

public class GUITextureBuilder implements IGUITextureBuilder {

	private String textureName;
	private Vec2f position;
	private Vec2f scale;

	@Override
	public IGUITextureBuilder setTextureName(String textureName) {
		this.textureName = textureName;
		return this;
	}

	@Override
	public IGUITextureBuilder setPosition(Vec2f position) {
		this.position = position;
		return this;
	}

	@Override
	public IGUITextureBuilder setScale(Vec2f scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public GUITexture build(String name) {
		Loader loader = Loader.getInstance();
		Texture texture = loader.getTextureLoader().loadGUITexture(EngineSettings.TEXTURE_INTERFACE_PATH, this.textureName);
		return new GUITexture(name, texture, this.position, this.scale);
	}

}
