package bodies;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import intersects.IntersectData;
import physicMain.PE10;
import toolbox.Maths;

public class Body3DSphere extends BodyBasic implements Body {

	public Body3DSphere(int id, Vector3f position, float size) {
		super(id, position, size);
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
	
	public IntersectData checkIntersection(Body body) {
		IntersectData data = null;
		if (body.getTypeID() == PE10.BODY_3D_SPHERE) {
			data = check2SphereIntersection(body);
		}
		return data;
	}
	
	private IntersectData check2SphereIntersection(Body body) {
		float distance = Maths.distance2Points(this.position, body.getPosition());
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
	
	public int getID() {
		return super.getID();
	}
	
	public int getTypeID() {
		return super.getTypeID();
	}
	
	public float getSize() {
		return super.getSize();
	}

}
