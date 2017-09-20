package object.light;

import object.Nameable;
import tool.math.vector.Vec3f;

public interface ILight extends Nameable {
	
	// вернуть затухание
	public Vec3f getAttenuation();
	public Vec3f getPosition();
	public void setPosition(Vec3f position);
	public Vec3f getColour();
	public void setColour(Vec3f colour);

}
