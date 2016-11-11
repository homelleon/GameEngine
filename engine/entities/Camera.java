package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import scene.EngineSettings;

public interface Camera {
	 
	public String getName();
	public void setPosition(float posX, float posY, float posZ);
	public void setPitch(float anglePitch);
	public void setYaw(float angleYaw);
	public void move();
	public Vector3f getPosition();
	public float getPitch();
	public void invertPitch();
	public float getYaw();
	public float getRoll();
	
}
