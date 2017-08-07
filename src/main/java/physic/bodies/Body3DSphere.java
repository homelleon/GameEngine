package bodies;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import intersects.IntersectData;
import object.entity.entity.IEntity;
import physicMain.PE10;
import tool.math.Maths;

public class Body3DSphere extends BodyBasic implements IBody {

	public Body3DSphere(Vector3f position, float size) {
		super(position, size);
		typeID = PE10.BODY_3D_SPHERE;
	}
	
	public Body3DSphere() {
		super();
		typeID = PE10.BODY_3D_SPHERE;
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
	
	@Override
	public void doAcceleration(float value, Vector3f direction) {
		
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	public void attachEntity(IEntity entity) {
		super.attachEntity(entity);
	}
	
	@Override
	public IEntity getEntity() {
		return super.getEntity();
	}
	
	@Override
	public IntersectData checkIntersection(IBody body) {
		IntersectData data = null;
		if (body.getTypeID() == PE10.BODY_3D_SPHERE) {
			data = check2SphereIntersection(body);
		}
		return data;
	}
	
	private IntersectData check2SphereIntersection(IBody body) {
		float distance = Maths.distance2Points(this.position, body.getPosition());
		System.out.println(this.position);
		System.out.println(body.getPosition());
		System.out.println(distance);
		float intersectDistance = distance - (this.getSize() + body.getSize());
		List<Vector3f> points = new ArrayList<Vector3f>();
		int type = PE10.DATA_IS_OUT;
		
		if (intersectDistance < 0) {
			Vector3f point1 = new Vector3f(0,0,0); //to calculate
			Vector3f point2 = new Vector3f(0,0,0); //to calculate
			points.add(point1);
			points.add(point2);	
			
		} else if (intersectDistance == 0) {
			Vector3f point = new Vector3f(0,0,0); //to calculate (center of points)
			points.add(point);
		}
		return new IntersectData(type, distance, points);
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
