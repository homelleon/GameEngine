package object.entity.entity.builder;

import object.entity.entity.IEntity;
import object.entity.entity.NormalMappedEntity;
import object.entity.entity.TexturedEntity;
import primitive.model.Model;
import tool.math.vector.Vector3fF;

public class EntityBuilder implements IEntityBuilder {
	
	private Model model;
	private float scale = 1.0f;
	private Vector3fF position = new Vector3fF(0,0,0);
	private Vector3fF rotation = new Vector3fF(0,0,0);
	private int textureIndex = 0;
	
	@Override
	public IEntityBuilder setModel(Model model) {
		this.model = model;
		return this;
	}

	@Override
	public IEntityBuilder setScale(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public IEntityBuilder setPosition(Vector3fF position) {
		this.position = position;
		return this;
	}

	@Override
	public IEntityBuilder setRotation(Vector3fF rotation) {
		this.rotation = rotation;
		return this;
	}
	
	@Override
	public IEntityBuilder setTextureIndex(int index) {
		this.textureIndex = index;
		return this;
	}

	@Override
	public IEntity build(String name) {
		if(model!= null) {
			if(model.getMaterial().getNormalMap()!= null) {
				return new NormalMappedEntity(name, model, textureIndex, position, rotation, scale);
			} else {
				return new TexturedEntity(name, model, textureIndex, position, rotation, scale);
			}
		} else {
			throw new NullPointerException("No model defined for entity builder!");
		}
	}
	
}