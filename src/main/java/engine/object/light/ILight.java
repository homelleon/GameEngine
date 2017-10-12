package object.light;

import object.Nameable;
import tool.math.vector.Vector3f;

/**
 * 
 * @author homelleon
 * @version 1.0
 * @see Light
 */
public interface ILight extends Nameable {
	
	// вернуть затухание
	public Vector3f getAttenuation();
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	public Vector3f getColor();
	public void setColor(Vector3f color);

}
