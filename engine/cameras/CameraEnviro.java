package cameras;

import org.lwjgl.util.vector.Vector3f;

public class CameraEnviro {
	
private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 20;
	private float yaw = 0; 
	private float roll;
	
	private String name;
	
	public CameraEnviro(String name, float posX,float posY,float posZ) {
		this.setPosition(posX, posY, posZ);		
		this.name = name;
	}
	
	public void setPosition(float posX, float posY, float posZ) {
		this.position.x = posX;
		this.position.y = posY;
		this.position.z = posZ;
	}
	
	public void setPitch(float anglePitch) {
		this.pitch = anglePitch;
	}
	
	public void setYaw(float angleYaw) {
		this.yaw = angleYaw;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}
	
	public void invertPitch() {
		this.pitch = -pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}	

}
