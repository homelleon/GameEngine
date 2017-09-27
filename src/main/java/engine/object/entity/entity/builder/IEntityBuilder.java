package object.entity.entity.builder;

import object.entity.entity.IEntity;
import primitive.model.Model;
import tool.math.vector.Vector3fF;

public interface IEntityBuilder {
	
	public IEntityBuilder setModel(Model model);

	public IEntityBuilder setScale(float scale);

	public IEntityBuilder setPosition(Vector3fF position);

	public IEntityBuilder setRotation(Vector3fF rotation);
	
	public IEntityBuilder setTextureIndex(int index);

	public IEntity build(String name);

}
