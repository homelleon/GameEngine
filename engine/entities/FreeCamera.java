package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import scene.Settings;

public class FreeCamera implements Camera{
	
	private static final float MAX_PITCH = 90;
	private static final float MIN_PITCH = -90;
	private static final float SPEED = 0.5f;
	private static final float RUN_SPEED = 4;
	
	private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 20;
	private float yaw = 0;
	private float roll;
	
	private String name;
	
	public boolean perspectiveMode = false;
	public boolean isUnderWater = false;
	
	
	public String getName() {
		return name;
	}

	public FreeCamera(float posX,float posY,float posZ) {
		this.setPosition(posX, posY, posZ);		
		this.name = "FreeCamera";
	}
	
	public FreeCamera(String name, float posX,float posY,float posZ) {
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
	
	public void move() {
			calculatePitchAndAngle();
			float runSpeed = 1;
					
			if(Keyboard.isKeyDown(Keyboard.KEY_HOME)) {
				roll = 0;
				pitch = 0;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				runSpeed = RUN_SPEED;
			}
		
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				position.z-= SPEED * runSpeed;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				position.z+= SPEED * runSpeed;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				position.x-= SPEED * runSpeed;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				position.x+= SPEED * runSpeed;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				position.y+=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_C)) {
				position.y-=0.5f;
			}
					
			if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
				roll+=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
				roll-=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				yaw+=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				yaw-=0.5f;
		  	}
			
	    	if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
	 			pitch-=0.1f;
			}
	    	
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				pitch+=0.1f;
			}			
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
	
	private void calculatePitchAndAngle() {
		if(Mouse.isButtonDown(2)) {
			float pitchChange = Mouse.getDY() * Settings.MOUSE_Y_SPEED;
			
			if((pitch<MAX_PITCH)||(pitch>MIN_PITCH)) {
				pitch -= pitchChange;
			}
		}

		if(Mouse.isButtonDown(2)) {	
			float angleChange = Mouse.getDX() * Settings.MOUSE_X_SPEED;			
		}
	}
	
	
}
