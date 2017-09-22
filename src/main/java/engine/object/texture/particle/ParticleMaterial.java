package object.texture.particle;

import object.texture.Texture2D;

public class ParticleMaterial {

	private Texture2D texture;
	private boolean additive;

	public ParticleMaterial(Texture2D texture, boolean additive) {
		this.texture = texture;
		this.additive = additive;
	}

	public Texture2D getTexture() {
		return texture;
	}

	public boolean isAdditive() {
		return additive;
	}

}