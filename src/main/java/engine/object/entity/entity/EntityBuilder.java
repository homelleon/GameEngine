package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

import object.model.textured.TexturedModel;

public class EntityBuilder implements IEntityBuilder {
	
	private TexturedModel model;
	private float scale = 1.0f;
	private Vector3f position = new Vector3f(0,0,0);
	private Vector3f rotation = new Vector3f(0,0,0);
	
	@Override
	public IEntityBuilder setModel(TexturedModel model) {
		this.model = model;
		return this;
	}

	@Override
	public IEntityBuilder setScale(float scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public IEntityBuilder setPosition(Vector3f position) {
		this.position = position;
		return this;
	}

	@Override
	public IEntityBuilder setRotation(Vector3f rotation) {
		this.rotation = rotation;
		return this;
	}

	@Override
	public IEntity createEntity(String name) {
		if(model!= null) {
			if(model.getTexture().getNormalMap()!= 0) {
				return new NormalMappedEntity(name, model, position, rotation, scale);
			} else {
				return new TexturedEntity(name, model, position, rotation, scale);
			}
		} else {
			throw new NullPointerException("No model defined for entity builder!");
		}
	}
	
}