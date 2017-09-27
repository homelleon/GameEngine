package object.light;

import object.Nameable;
import tool.math.vector.Vector3fF;

public interface ILight extends Nameable {
	
	// вернуть затухание
	public Vector3fF getAttenuation();
	public Vector3fF getPosition();
	public void setPosition(Vector3fF position);
	public Vector3fF getColour();
	public void setColour(Vector3fF colour);

}
