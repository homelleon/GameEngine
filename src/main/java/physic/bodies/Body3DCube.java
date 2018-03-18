package bodies;

import intersects.IntersectData;
import object.entity.Entity;
import physicMain.PE10;
import tool.math.vector.Vector3f;

public class Body3DCube extends BodyBasic implements IBody {

	public Body3DCube(Vector3f position, float size) {
		super(position, size);
		typeID = PE10.BODY_3D_CUBE;
	}
	
	public Body3DCube() {
		super();
		typeID = PE10.BODY_3D_CUBE;
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
	public IntersectData checkIntersection(IBody body) {
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
