package object.entity.entity.builder;

import object.entity.Entity;
import primitive.model.Model;
import tool.math.vector.Vector3f;

/**
 * 
 * @author homelleon
 * @see EntityBuilder
 */
public interface IEntityBuilder {
	
	public IEntityBuilder setModel(Model model);

	public IEntityBuilder setScale(float scale);

	public IEntityBuilder setPosition(Vector3f position);

	public IEntityBuilder setRotation(Vector3f rotation);
	
	public IEntityBuilder setTextureIndex(int index);

	public Entity build(String name);

}
