package scene;

import object.Nameable;
import tool.math.vector.Vector;

public abstract class Subject<T extends Vector> implements Nameable {
	
	protected String name;
	protected T position;
	protected T rotation;
	
	protected Subject(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public T getPosition() {
		return position;
	}
	
	public Subject setPosition(T position) {
		this.position = (T) position.clone();
		return this;
	}
	
	public T getRotation() {
		return rotation;
	}
	
	public Subject setRotation(T rotation) {
		this.rotation = (T) rotation.clone();
		return this;
	}

}
