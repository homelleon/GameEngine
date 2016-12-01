package bodies;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import intersects.IntersectData;
import physicMain.PE10;

public class Body2DTriangle extends BodyBasic implements Body {

	public Body2DTriangle(Vector3f position, float size) {
		super(position, size);
		typeID = PE10.BODY_2D_TRIANGLE;
	}
	
	public Body2DTriangle() {
		super();
		typeID = PE10.BODY_2D_TRIANGLE;
	}

	public Vector3f getPosition() {
		return super.getPosition();
	}
	
	public void setPosition(Vector3f position) {
		super.setPosition(position);
	}
	
	public float getMass() {
		return super.getMass();
	}
	
	public void doAcceleration(float value, Vector3f direction) {
		
	}
	
	public IntersectData checkIntersection(Body body) {
		IntersectData data = null;
		return data;		
	}
	
	public void attachEntity(Entity entity) {
		super.attachEntity(entity);
	}
	
	public Entity getEntity() {
		return super.getEntity();
	}
	
	public int getTypeID() {
		return super.getTypeID();
	}
	
	public float getSize() {
		return super.getSize();
	}

}
