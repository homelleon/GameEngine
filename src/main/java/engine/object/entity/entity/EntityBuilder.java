package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

public abstract class EntityBuilder {
	protected String modelName;
	protected String textureName;
	protected String normalTextureName;
	protected String specularTextureName;
	protected float shiness = 0.0f;
	protected float reflectivity = 0.0f;
	protected float scale;
	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);

	public EntityBuilder setModel(String modelName) {
		this.modelName = modelName;
		return this;
	}

	public EntityBuilder setTexture(String textureName) {
		this.textureName = textureName;
		return this;
	}
	
	public EntityBuilder setNormaTexture(String textureName) {
		this.normalTextureName = textureName;
		return this;
	}
	
	public EntityBuilder setSpecularTexture(String textureName) {
		this.specularTextureName = textureName;
		return this;
	}
	
	public EntityBuilder setTextureShiness(float shiness) {
		this.shiness = shiness;
		return this;
	}
	
	public EntityBuilder setTextureReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
		return this;
	}

	public EntityBuilder setScale(float scale) {
		this.scale = scale;
		return this;
	}

	public EntityBuilder setPosition(Vector3f position) {
		this.position = position;
		return this;
	}

	public EntityBuilder setRotation(Vector3f rotation) {
		this.rotation = rotation;
		return this;
	}

	public abstract Entity createEntity(String name);

}
