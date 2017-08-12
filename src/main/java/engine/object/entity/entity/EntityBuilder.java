package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.texture.model.ModelTexture;

public abstract class EntityBuilder {
	protected String modelName;
	protected TexturedModel texturedModel;
	protected boolean texturedModelIsLoaded = false;
	protected RawModel rawModel;
	protected boolean rawModelIsLoaded = false;
	protected String textureName;
	protected ModelTexture texture;
	protected boolean modelTextureIsLoader = false;
	protected String normalTextureName;
	protected ModelTexture normalTexture;
	protected boolean normalTextureIsLoader = false;
	protected String specularTextureName;
	protected ModelTexture specularTexture;
	protected boolean specularTextureIsLoader = false;
	protected float shiness = 0.0f;
	protected float reflectivity = 0.0f;
	protected float scale;
	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);

	public EntityBuilder setModel(String modelName) {
		this.modelName = modelName;
		return this;
	}
	
	public EntityBuilder setModel(String modelName, RawModel rawModel) {
		this.modelName = modelName;
		this.rawModel = rawModel;
		this.rawModelIsLoaded = true;
		return this;
	}

	public EntityBuilder setTexture(String textureName) {
		this.textureName = textureName;
		return this;
	}
	
	public EntityBuilder setTexture(ModelTexture texture) {
		this.textureName = texture.getName();
		this.texture = texture;
		this.modelTextureIsLoader = true;
		return this;
	}
	
	public EntityBuilder setNormalTexture(String textureName) {
		this.normalTextureName = textureName;
		return this;
	}
	
	public EntityBuilder setNormalTexture(ModelTexture texture) {
		this.normalTextureName = texture.getName();
		this.normalTexture = texture;
		this.normalTextureIsLoader = true;
		return this;
	}
	
	public EntityBuilder setSpecularTexture(String textureName) {
		this.specularTextureName = textureName;
		return this;
	}
	
	public EntityBuilder setSpecularTexture(ModelTexture texture) {
		this.specularTextureName = texture.getName();
		this.texture = texture;
		this.specularTextureIsLoader = true;
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

	public abstract IEntity createEntity(String name);

}
