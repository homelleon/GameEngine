package bodies;

import org.lwjgl.util.vector.Vector3f;

import physicMain.PE10;

public class Body2DQuad extends BodyBasic implements Body {

	protected Body2DQuad(int id, Vector3f position, float size) {
		super(id, position, size);
		typeID = PE10.PE_2D_QUAD;
	}

	@Override
	public Vector3f getPosition() {
		return null;
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

}
