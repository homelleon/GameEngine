package bodies;

import org.lwjgl.util.vector.Vector3f;

import intersects.IntersectData;
import object.entity.entity.Entity;
import physicMain.PE10;

public class Body3DPyramid extends BodyBasic implements BodyInterface {

	public Body3DPyramid(Vector3f position, float size) {
		super(position, size);
		typeID = PE10.BODY_3D_PYRAMID;
	}
	
	public Body3DPyramid() {
		super();
		typeID = PE10.BODY_3D_PYRAMID;
	}

	@Override
	public Vector3f getPosition() {
		return super.getPosition();
	}
	
	@Override
	public void setPosition(Vector3f position) {
		super.setPosition(position);
	}
	
	@Override
	public float getMass() {
		return super.getMass();
	}
	
	public void attachEntity(Entity entity) {
		super.attachEntity(entity);
	}
	
	@Override
	public Entity getEntity() {
		return super.getEntity();
	}
	
	@Override
	public void doAcceleration(float value, Vector3f direction) {
		
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public IntersectData checkIntersection(BodyInterface body) {
		IntersectData data = null;
		return data;		
	}
	
	@Override
	public int getTypeID() {
		return super.getTypeID();
	}
	
	@Override
	public float getSize() {
		return super.getSize();
	}

}
