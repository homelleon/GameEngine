package bodies;

import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

public abstract class BodyBasic {
	
	protected int bodyID;
	protected int typeID;
	protected float mass = 0;
	protected float size;
	protected float vertex [][][];
	protected Vector3f position;
	protected Vector3f speed;
	
	protected BodyBasic(int id, Vector3f position, float size) {
		this.typeID = id;
		this.position = position;
		this.size = size;
	}
	
	protected BodyBasic(int id, Vector3f position, float[][][] vertex) {
		this.typeID = id;
		this.position = position;
		this.vertex = vertex;
	}
	
	protected int getID() {
		return bodyID;
	}
		
	protected int getTypeID() {
		return typeID;
	}
	
	protected float getMass() {
		return mass;
	}
	
	protected void setMass(float value) {
		this.mass = value;
	}
	
	protected float getSize() {
		return size;
	}
	
	protected void doAcceleration(float value, Vector3f direction) {
		
	}
	
	protected void update() {
		
		float totalAcceleration = 0;
		float totalDirection = 0;		
		
	}

}
