package renderer.object.bounding;

import object.entity.entity.IEntity;
import object.model.raw.RawModel;

public class EntityModelPair {
	
	private IEntity entity;
	private RawModel model;
	
	public EntityModelPair(RawModel model) {
		this.model = model;
	}
	
	public IEntity getEntity() {
		return this.entity;
	}
	
	public RawModel getModel() {
		return this.model;
	}
	
	public EntityModelPair setEntity(IEntity entity) {
		this.entity = entity;
		return this;
	}

}
