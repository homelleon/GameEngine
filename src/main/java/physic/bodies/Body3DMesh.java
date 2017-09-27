package bodies;

import intersects.IntersectData;
import object.entity.entity.IEntity;
import physicMain.PE10;
import tool.math.vector.Vector3fF;

public class Body3DMesh extends BodyBasic implements IBody {

	public Body3DMesh(Vector3fF position, float size) {
		super(position, size);
		typeID = PE10.BODY_3D_MESH;
	}
	
	public Body3DMesh() {
		super();
		typeID = PE10.BODY_3D_MESH;
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
	
	public void attachEntity(IEntity entity) {
		super.attachEntity(entity);
	}
	
	@Override
	public IEntity getEntity() {
		return super.getEntity();
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
	
	@Override
	public int getTypeID() {
		return super.getTypeID();
	}
	
	@Override
	public float getSize() {
		return super.getSize();
	}

}
