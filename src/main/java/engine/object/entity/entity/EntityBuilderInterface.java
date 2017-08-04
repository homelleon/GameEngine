package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

public interface EntityBuilderInterface {
	
	public EntityBuilder setModel(String modelName);
	
	public EntityBuilder setTexture(String textureName);
	
	public EntityBuilder setScale(float scale);

	public EntityBuilder setPosition(Vector3f position);

	public EntityBuilder setRotation(Vector3f rotation);

	public Entity createEntity(String name);

}
