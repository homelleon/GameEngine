package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import scene.EngineSettings;

public class PlayerCamera implements Camera{
	
	private static final float maxDistanceFromPlayer = 100;
	private static final float minDistanceFromPlayer = 0;
	private static final float maxPitch = 90;
	private static final float minPitch = -90;
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 20; 
	private float yaw = 0;
	private float roll;
	
	private String name;
	
	private Player player;
	
	public boolean perspectiveMode = false;
	public boolean isUnderWater = false;
	
	
	public String getName() {
		return name;
	}

	public PlayerCamera(Player player) {
		this.player = player;
		this.name = "NoName";
	}
	
	public PlayerCamera(Player player, String name) {
		this.player = player;
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
		if (perspectiveMode == false) {
			calculateZoom();
			calculatePitchAndAngle();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCameraPosition(horizontalDistance,verticalDistance);
			this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		}
		
		
		if (perspectiveMode) {
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				position.z-=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				position.z+=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				position.x+=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				position.y+=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_C)) {
				position.y-=0.5f;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				position.x-=0.5f;
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
	
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + 5 + verticDistance;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * EngineSettings.MOUSE_ZOOM_SPEED;
		if(((distanceFromPlayer<maxDistanceFromPlayer)&&(zoomLevel<0))
				||((distanceFromPlayer>minDistanceFromPlayer)&&(zoomLevel>0))) {
			distanceFromPlayer -= zoomLevel;
		}
	}
	
	private void calculatePitchAndAngle() {
		if(!Mouse.isButtonDown(2)) {
			float pitchChange = (Mouse.getY() - EngineSettings.DISPLAY_HEIGHT/2) * EngineSettings.MOUSE_Y_SPEED;
			
			if((pitch<maxPitch)||(pitch>minPitch)) {
				pitch -= pitchChange;
			}
		}
		
		if(Mouse.isButtonDown(2)) {	
			float angleChange = (Mouse.getX() - EngineSettings.DISPLAY_WIDTH/2) * EngineSettings.MOUSE_X_SPEED;
			angleAroundPlayer = -angleChange;		
		}else{
			angleAroundPlayer = 0;
		}
	}
	
	private void underWaterCalculate() {
		if(this.position.y <=0) {
			isUnderWater = true;
		} else {
			isUnderWater = false;
		}
	}
	
}
