package bodies;

import intersects.IntersectData;
import object.entity.entity.IEntity;
import tool.math.vector.Vector3fF;

public abstract class BodyBasic {
	
	protected int typeID;
	protected float mass = 0;
	protected float size;
	protected float vertex [][][];
	protected Vector3fF position;
	protected Vector3fF speed;
	protected IEntity entity;
	protected boolean entityAttached = false;
	
	protected BodyBasic(Vector3fF position, float size) {
		this.position = position;
		this.size = size;
	}
	
	protected BodyBasic() {}
	
	protected BodyBasic(int id, Vector3fF position, float[][][] vertex) {
		this.typeID = id;
		this.position = position;
		this.vertex = vertex;
	}
	
	protected void setPosition(Vector3fF position) {
		this.position = position;
	}
	
	protected Vector3fF getPosition() {
		return this.position;
	}
		
	protected int getTypeID() {
		return this.typeID;
	}
	
	protected float getMass() {
		return this.mass;
	}
	
	protected void setMass(float value) {
		this.mass = value;
	}
	
	protected float getSize() {
		return this.size;
	}
	
	protected boolean isEntityAttached() {
		return this.entityAttached;
	}
	
	protected void attachEntity(IEntity entity) {
		this.entity = entity;
		this.entityAttached = true;
		this.position = entity.getPosition();
	}
	
	protected IEntity getEntity() {
		return entity;
	}
	
	protected void doAcceleration(float value, Vector3fF direction) {
		
	}
	
	protected void update() {
		float totalAcceleration = 0;
		float totalDirection = 0;
		entity.setPosition(this.position);
	}
	
	
	protected IntersectData checkIntersection(IBody body) {
		IntersectData data = null;
		return data;
	}
	

	protected void delete() {
		this.entity = null;
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
