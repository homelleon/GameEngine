package object.entity.entity;

import java.util.Collection;

import core.settings.EngineSettings;
import object.entity.AEntity;
import primitive.model.Model;
import tool.math.vector.Vector3f;

public class DecorEntity extends AEntity implements IEntity {

	public DecorEntity(String name, Collection<Model> modelList, int textureIndex, Vector3f position,
			Vector3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_DECORATE, modelList, textureIndex, position, rotation, scale);
	}
	
	public DecorEntity(String name, Collection<Model> modelList, Vector3f position,
			Vector3f rotation, float scale) {
		super(name, EngineSettings.ENTITY_TYPE_DECORATE, modelList, position, rotation, scale);
	}

	@Override
	public IEntity clone(String name) {
		IEntity entity = new DecorEntity(name, this.models, this.textureIndex, new Vector3f(this.position), new Vector3f(this.rotation), this.scale);
		entity.setBaseName(this.getName());
		return entity;
	}

}
