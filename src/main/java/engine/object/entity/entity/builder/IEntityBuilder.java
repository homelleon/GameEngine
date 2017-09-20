package object.entity.entity.builder;

import object.entity.entity.IEntity;
import object.model.textured.TexturedModel;
import tool.math.vector.Vec3f;

public interface IEntityBuilder {
	
	public IEntityBuilder setModel(TexturedModel model);

	public IEntityBuilder setScale(float scale);

	public IEntityBuilder setPosition(Vec3f position);

	public IEntityBuilder setRotation(Vec3f rotation);
	
	public IEntityBuilder setTextureIndex(int index);

	public IEntity build(String name);

}
