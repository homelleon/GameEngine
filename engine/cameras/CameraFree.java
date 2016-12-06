package cameras;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import engineMain.DisplayManager;
import scene.ES;

public class CameraFree implements Camera {
	
	private static final float SPEED = 100;
	private static final float RUN_SPEED = 4;
	
	private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 20;
	private float yaw = 0; 
	private float roll;
	
	private float currentForwardSpeed = 0;
	private float currentStrafeSpeed = 0;
	private float currentTurnSpeed = 0;
	private float currentPitchSpeed = 0;
	
	private String name;
	
	public boolean perspectiveMode = false;
	public boolean isUnderWater = false;
	
	
	public String getName() {
		return name;
	}

	public CameraFree(float posX,float posY,float posZ) {
		this.setPosition(posX, posY, posZ);		
		this.name = "FreeCamera";
	}
	
	public CameraFree(String name, float posX,float posY,float posZ) {
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
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.roll += dx;
		this.yaw += dy;
		this.pitch += dz;
	}
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	public void move() {
		chekInputs();
		float yawAngle = currentTurnSpeed * DisplayManager.getFrameTimeSeconds(); 
		float pitchAngle = currentPitchSpeed * DisplayManager.getFrameTimeSeconds();
		float strafeDistance = currentStrafeSpeed * DisplayManager.getFrameTimeSeconds();
		float fowardDistance = currentForwardSpeed * DisplayManager.getFrameTimeSeconds();
		
		increaseRotation(0, yawAngle, pitchAngle);
		
		float dx = (float) (fowardDistance * Math.sin(Math.toRadians(-yaw)) 
				+ (strafeDistance * Math.sin(Math.toRadians(-yaw + 90))));
		float dy = (float) (fowardDistance * Math.sin(Math.toRadians(pitch)) 
				+ strafeDistance * Math.cos(Math.toRadians(pitch + 90)));
		float dz = (float) (fowardDistance * Math.cos(Math.toRadians(-yaw)) 
				+ strafeDistance * Math.cos(Math.toRadians(-yaw + 90)));
		
		increasePosition(dx, dy, dz);
				
	}
	
	private void chekInputs() {
		if(Keyboard.isKeyDown(ES.KEY_EDITOR_CENTER_VIEW)) {
			roll = 0;
			pitch = 0;
		}
		
		float runSpeed = 1;
		
		if(Keyboard.isKeyDown(ES.KEY_EDITOR_ACCELERATE)) {
			runSpeed = RUN_SPEED;
		}
	
		if(Keyboard.isKeyDown(ES.KEY_EDITOR_MOVE_FORWARD)) {
			currentForwardSpeed = -SPEED * runSpeed;
		} else if(Keyboard.isKeyDown(ES.KEY_EDITOR_MOVE_BACKWARD)) {
			currentForwardSpeed = SPEED * runSpeed;;
		} else {
			currentForwardSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(ES.KEY_EDITOR_MOVE_LEFT)) {
			currentStrafeSpeed = -SPEED * runSpeed;
		} else if(Keyboard.isKeyDown(ES.KEY_EDITOR_MOVE_RIGHT)) {
			currentStrafeSpeed = SPEED * runSpeed;
		} else {
			currentStrafeSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(ES.KEY_EDITOR_MOVE_UP)) {
			position.y += 0.5f;
		}
		
		if(Keyboard.isKeyDown(ES.KEY_EDITOR_MOVE_DOWN)) {
			position.y -= 0.5f;
		}
					
		if(Mouse.isButtonDown(2)) {
			this.currentTurnSpeed = SPEED * Mouse.getDX() * ES.MOUSE_X_SPEED * runSpeed;
			this.currentPitchSpeed = -SPEED * Mouse.getDY() * ES.MOUSE_Y_SPEED * runSpeed;	
		} else {
			currentTurnSpeed = 0;
			currentPitchSpeed = 0;
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
		
	
}
