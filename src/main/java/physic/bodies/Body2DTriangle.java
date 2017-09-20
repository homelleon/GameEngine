package bodies;

import intersects.IntersectData;
import object.entity.entity.IEntity;
import physicMain.PE10;
import tool.math.vector.Vec3f;

public class Body2DTriangle extends BodyBasic implements IBody {

	public Body2DTriangle(Vec3f position, float size) {
		super(position, size);
		typeID = PE10.BODY_2D_TRIANGLE;
	}
	
	public Body2DTriangle() {
		super();
		typeID = PE10.BODY_2D_TRIANGLE;
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
	
	public void attachEntity(IEntity entity) {
		super.attachEntity(entity);
	}
	
	@Override
	public IEntity getEntity() {
		return super.getEntity();
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
