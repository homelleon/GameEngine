package scene;

import tool.math.vector.Vector;

public abstract class Member {
	
	protected String name;
	protected Vector position;
	protected Vector rotation;
	
	protected Member(String name) {
		this.name = name;
	}
	
	String getName() {
		return name;
	}
	
	Vector getPosition() {
		return position;
	}
	
	Member setPosition(Vector position) {
		this.position = position.clone();
		return this;
	}
	
	Vector getRotation() {
		return rotation;
	}
	
	Member setRotation(Vector rotation) {
		this.rotation = rotation.clone();
		return this;
	}

}
