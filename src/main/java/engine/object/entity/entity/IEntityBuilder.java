package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

import object.model.raw.RawModel;
import object.texture.model.ModelTexture;

public interface IEntityBuilder {
	
	public EntityBuilder setModel(String modelName);
	
	public EntityBuilder setModel(String modelName, RawModel rawModel);
	
	public EntityBuilder setTexture(String textureName);
	
	public EntityBuilder setTexture(ModelTexture texture);
	
	public EntityBuilder setNormalTexture(String textureName);
	
	public EntityBuilder setNormalTexture(ModelTexture texture);
	
	public EntityBuilder setSpecularTexture(String textureName);
	
	public EntityBuilder setSpecularTexture(ModelTexture texture);
	
	public EntityBuilder setTextureShiness(float shiness);
	
	public EntityBuilder setTextureReflectivity(float reflectivity);
	
	public EntityBuilder setScale(float scale);

	public EntityBuilder setPosition(Vector3f position);

	public EntityBuilder setRotation(Vector3f rotation);

	public IEntity createEntity(String name);

}
