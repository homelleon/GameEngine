package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

import object.model.raw.RawModel;
import object.model.textured.TexturedModel;
import object.texture.model.ModelTexture;

public interface IEntityBuilder {
	
	public IEntityBuilder setModel(TexturedModel model);

	public IEntityBuilder setScale(float scale);

	public IEntityBuilder setPosition(Vector3f position);

	public IEntityBuilder setRotation(Vector3f rotation);

	public IEntity createEntity(String name);

}
