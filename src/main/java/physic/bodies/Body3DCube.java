package bodies;

import intersects.IntersectData;
import object.entity.entity.IEntity;
import physicMain.PE10;
import tool.math.vector.Vec3f;

public class Body3DCube extends BodyBasic implements IBody {

	public Body3DCube(Vec3f position, float size) {
		super(position, size);
		typeID = PE10.BODY_3D_CUBE;
	}
	
	public Body3DCube() {
		super();
		typeID = PE10.BODY_3D_CUBE;
	}

	@Override
	public Vec3f getPosition() {
		return super.getPosition();
	}
	
	@Override
	public void setPosition(Vec3f position) {
		super.setPosition(position);
	}
	
	@Override
	public float getMass() {
		return super.getMass();
	}
	
	public void attachEntity(IEntity entity) {
		super.attachEntity(entity);
	}
	
	@Override
	public IEntity getEntity() {
		return super.getEntity();
	}
	
	@Override
	public void doAcceleration(float value, Vec3f direction) {
		
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
