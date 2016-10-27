package engine.models;

import engine.textures.ModelTexture;

public class TexturedModel {
	
	private RawModel rawModel;
	private ModelTexture texture;
	private String name;
	
	public TexturedModel(RawModel model, ModelTexture texture) {
		this.rawModel = model;
		this.texture = texture;
		this.name = "NoName";
	}
	
	public TexturedModel(String name, RawModel model, ModelTexture texture) {
		this.rawModel = model;
		this.texture = texture;
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}

}
