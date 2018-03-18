package renderer.bounding;

import object.entity.Entity;
import primitive.model.Mesh;

public class EntityModelPair {
	
	private Entity entity;
	private Mesh model;
	
	public EntityModelPair(Mesh model) {
		this.model = model;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public Mesh getModel() {
		return this.model;
	}
	
	public EntityModelPair setEntity(Entity entity) {
		this.entity = entity;
		return this;
	}

}
