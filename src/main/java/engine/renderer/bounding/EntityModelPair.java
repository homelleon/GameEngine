package renderer.bounding;

import object.entity.entity.IEntity;
import primitive.model.Mesh;

public class EntityModelPair {
	
	private IEntity entity;
	private Mesh model;
	
	public EntityModelPair(Mesh model) {
		this.model = model;
	}
	
	public IEntity getEntity() {
		return this.entity;
	}
	
	public Mesh getModel() {
		return this.model;
	}
	
	public EntityModelPair setEntity(IEntity entity) {
		this.entity = entity;
		return this;
	}

}
