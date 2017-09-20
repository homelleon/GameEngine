package object.entity.entity.builder;

import object.entity.entity.IEntity;
import object.entity.entity.NormalMappedEntity;
import object.entity.entity.TexturedEntity;
import object.model.textured.TexturedModel;
import tool.math.vector.Vec3f;

public class EntityBuilder implements IEntityBuilder {
	
	private TexturedModel model;
	private float scale = 1.0f;
	private Vec3f position = new Vec3f(0,0,0);
	private Vec3f rotation = new Vec3f(0,0,0);
	private int textureIndex = 0;
	
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
	public IEntityBuilder setPosition(Vec3f position) {
		this.position = position;
		return this;
	}

	@Override
	public IEntityBuilder setRotation(Vec3f rotation) {
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
			if(model.getTexture().getNormalMap()!= 0) {
				return new NormalMappedEntity(name, model, textureIndex, position, rotation, scale);
			} else {
				return new TexturedEntity(name, model, textureIndex, position, rotation, scale);
			}
		} else {
			throw new NullPointerException("No model defined for entity builder!");
		}
	}
	
}