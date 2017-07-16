package bodies;

import org.lwjgl.util.vector.Vector3f;

import intersects.IntersectData;
import object.entity.entity.EntityInterface;
import physicMain.PE10;

public class Body2DQuad extends BodyBasic implements BodyInterface {

	public Body2DQuad(Vector3f position, float size) {
		super(position, size);
		typeID = PE10.BODY_2D_QUAD;
	}
	
	public Body2DQuad() {
		super();
		typeID = PE10.BODY_2D_QUAD;
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
	
	public void update() {
		super.update();
	}
	
	public IntersectData checkIntersection(BodyInterface body) {
		IntersectData data = null;
		return data;		
	}
	
	public void attachEntity(EntityInterface entity) {
		super.attachEntity(entity);
	}
	
	public EntityInterface getEntity() {
		return super.getEntity();
	}
	
	public int getTypeID() {
		return super.getTypeID();
	}
	
	public float getSize() {
		return super.getSize();
	}

}
