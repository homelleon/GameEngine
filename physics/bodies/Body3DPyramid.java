package bodies;

import org.lwjgl.util.vector.Vector3f;

import physicMain.PE10;

public class Body3DPyramid extends BodyBasic implements Body {

	public Body3DPyramid(int id, Vector3f position, float size) {
		super(id, position, size);
		typeID = PE10.BODY_3D_PYRAMID;
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
