package object.entity.entity.builder;

import org.lwjgl.util.vector.Vector3f;

import object.entity.entity.IEntity;
import object.model.textured.TexturedModel;

public interface IEntityBuilder {
	
	public IEntityBuilder setModel(TexturedModel model);

	public IEntityBuilder setScale(float scale);

	public IEntityBuilder setPosition(Vector3f position);

	public IEntityBuilder setRotation(Vector3f rotation);

	public IEntity build(String name);

}
