package object.light;

import org.lwjgl.util.vector.Vector3f;

import object.Nameable;

public interface ILight extends Nameable {
	
	// ������� ���������
	public Vector3f getAttenuation();
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	public Vector3f getColour();
	public void setColour(Vector3f colour);

}
