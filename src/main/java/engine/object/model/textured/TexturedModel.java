package object.model.textured;

import object.model.raw.RawModel;
import object.texture.model.ModelTexture;

public class TexturedModel {

	private RawModel rawModel;
	private ModelTexture texture;
	private String name;

	public TexturedModel(String name, RawModel model, ModelTexture texture) {
		this.rawModel = model;
		this.texture = texture;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public RawModel getRawModel() {
		return this.rawModel;
	}

	public ModelTexture getTexture() {
		return this.texture;
	}

}
