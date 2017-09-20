package object.entity.entity;

import core.settings.EngineSettings;
import object.entity.BaseEntity;
import object.model.textured.TexturedModel;
import tool.math.vector.Vec3f;

public class DecorEntity extends BaseEntity implements IEntity {

	public DecorEntity(String name, TexturedModel model, int textureIndex, Vec3f position,
			Vec3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_DECORATE, model, textureIndex, position, rotation, scale);
	}
	
	public DecorEntity(String name, TexturedModel model, Vec3f position,
			Vec3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_DECORATE, model, position, rotation, scale);
	}

	@Override
	public IEntity clone(String name) {
		IEntity entity = new DecorEntity(name, this.model, this.textureIndex, this.position, this.rotation, this.scale);
		entity.setBaseName(this.getName());
		return entity;
	}

}
