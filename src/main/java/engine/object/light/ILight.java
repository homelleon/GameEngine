package object.light;

import object.Nameable;
import tool.math.vector.Vector3f;

public interface ILight extends Nameable {
	
	// вернуть затухание
	public Vector3f getAttenuation();
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	public Vector3f getColour();
	public void setColour(Vector3f colour);

}
