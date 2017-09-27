package bodies;

import intersects.IntersectData;
import object.entity.entity.IEntity;
import physicMain.PE10;
import tool.math.vector.Vector3fF;

public class Body2DQuad extends BodyBasic implements IBody {

	public Body2DQuad(Vector3fF position, float size) {
		super(position, size);
		typeID = PE10.BODY_2D_QUAD;
	}
	
	public Body2DQuad() {
		super();
		typeID = PE10.BODY_2D_QUAD;
	}

	@Override
	public Vector3fF getPosition() {
		return super.getPosition();
	}
	
	@Override
	public void setPosition(Vector3fF position) {
		super.setPosition(position);
	}
	
	@Override
	public float getMass() {
		return super.getMass();
	}
	
	@Override
	public void doAcceleration(float value, Vector3fF direction) {
		
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
