package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0,-10,0);
	
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera(){}
	
	public Camera(float posX,float posY,float posZ){
		this.setPosition(posX, posY, posZ);		
	}
	
	public void setPosition(float posX, float posY, float posZ) {
		this.position.x = posX;
		this.position.y = posY;
		this.position.z = posZ;
	}
	
	public void setPitch(float anglePitch){
		this.pitch = anglePitch;
	}
	
	public void setYaw(float angleYaw){
		this.yaw = angleYaw;
	}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z-=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z+=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x+=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y+=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_C)){
			position.y-=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x-=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			roll+=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			roll-=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw+=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw-=0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			pitch-=0.1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			pitch+=0.1f;
		}
		
		
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

}
