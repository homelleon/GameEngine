package bodies;

import org.lwjgl.util.vector.Vector3f;

import physicMain.PE10;

public class Body2DPlane extends BodyBasic implements Body {

	public Body2DPlane(int id, Vector3f position, float size) {
		super(id, position, size);
		typeID = PE10.BODY_2D_PLANE;
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
