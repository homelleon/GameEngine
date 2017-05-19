package bodies;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.EntityInterface;
import intersects.IntersectData;
import physicMain.PE10;
import toolbox.Maths;

public class Body3DSphere extends BodyBasic implements BodyInterface {

	public Body3DSphere(Vector3f position, float size) {
		super(position, size);
		typeID = PE10.BODY_3D_SPHERE;
	}
	
	public Body3DSphere() {
		super();
		typeID = PE10.BODY_3D_SPHERE;
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
	
	public void attachEntity(EntityInterface entity) {
		super.attachEntity(entity);
	}
	
	public EntityInterface getEntity() {
		return super.getEntity();
	}
	
	public IntersectData checkIntersection(BodyInterface body) {
		IntersectData data = null;
		if (body.getTypeID() == PE10.BODY_3D_SPHERE) {
			data = check2SphereIntersection(body);
		}
		return data;
	}
	
	private IntersectData check2SphereIntersection(BodyInterface body) {
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
	
	public int getTypeID() {
		return super.getTypeID();
	}
	
	public float getSize() {
		return super.getSize();
	}

}
