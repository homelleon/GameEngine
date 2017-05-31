package bodies;

import org.lwjgl.util.vector.Vector3f;

import intersects.IntersectData;
import objects.entities.EntityInterface;

public abstract class BodyBasic {
	
	protected int typeID;
	protected float mass = 0;
	protected float size;
	protected float vertex [][][];
	protected Vector3f position;
	protected Vector3f speed;
	protected EntityInterface entity;
	protected boolean entityAttached = false;
	
	protected BodyBasic(Vector3f position, float size) {
		this.position = position;
		this.size = size;
	}
	
	protected BodyBasic() {}
	
	protected BodyBasic(int id, Vector3f position, float[][][] vertex) {
		this.typeID = id;
		this.position = position;
		this.vertex = vertex;
	}
	
	protected void setPosition(Vector3f position) {
		this.position = position;
	}
	
	protected Vector3f getPosition() {
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
	
	protected void attachEntity(EntityInterface entity) {
		this.entity = entity;
		this.entityAttached = true;
		this.position = entity.getPosition();
	}
	
	protected EntityInterface getEntity() {
		return entity;
	}
	
	protected void doAcceleration(float value, Vector3f direction) {
		
	}
	
	protected void update() {
		float totalAcceleration = 0;
		float totalDirection = 0;
		entity.setPosition(this.position);
	}
	
	
	protected IntersectData checkIntersection(BodyInterface body) {
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
