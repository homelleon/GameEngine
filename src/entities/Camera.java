package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private static final float maxDistanceFromPlayer = 100;
	private static final float minDistanceFromPlayer = 20;
	private static final float maxPitch = 90;
	private static final float minPitch = 5;
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 20;
	private float yaw = 0;
	private float roll;
	
	private Player player;
	
	public boolean perspectiveMode = false;
	
	public Camera(Player player){
		this.player = player;
	}
	
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
		if (!perspectiveMode){
			calculateZoom();
			calculatePitchAndAngle();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCameraPosition(horizontalDistance,verticalDistance);
			this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		}
		
		
		if (perspectiveMode){
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
	
	private void calculateCameraPosition(float horizDistance, float verticDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance;
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		if(((distanceFromPlayer<maxDistanceFromPlayer)&&(zoomLevel<0))||((distanceFromPlayer>minDistanceFromPlayer)&&(zoomLevel>0))){
			distanceFromPlayer -= zoomLevel;
		}
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(2)){
			float pitchChange = Mouse.getDY() * 0.1f;
			if(((pitch<maxPitch)&&(pitchChange<0))||((pitch>minPitch)&&(pitchChange>0))){
				pitch -= pitchChange;
			}
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(2)){
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	private void calculatePitchAndAngle(){
		if(Mouse.isButtonDown(2)){
			float pitchChange = Mouse.getDY() * 0.1f;
			float angleChange = Mouse.getDX() * 0.2f;
			if(((pitch<maxPitch)&&(pitchChange<0))||((pitch>minPitch)&&(pitchChange>0))){
				pitch -= pitchChange;
			}
			angleAroundPlayer -= angleChange;		
		}
	}

}
