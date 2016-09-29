package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import scene.Settings;
import terrains.Terrain;

public class Player extends Entity {
	
	private static final float MOVE_SPEED = 20;
	private static final float RUN_SPEED = 40;
	private static final float TURN_SPEED = 40;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	private float currentForwardSpeed = 0;
	private float currentStrafeSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;
	
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(Terrain terrain){
		checkInputs();
		//super.setRotY(currentTurnSpeed);
		//super.setRotY(currentTurnSpeed);
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentForwardSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y < terrainHeight){
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump(){
		if(!isInAir){
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}
	
	private void checkInputs(){
		if((Keyboard.isKeyDown(Keyboard.KEY_W)) && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))){
			this.currentForwardSpeed = RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			this.currentForwardSpeed = MOVE_SPEED;			
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			this.currentForwardSpeed = -MOVE_SPEED; 
		}else{
			this.currentForwardSpeed = 0;	
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			this.currentStrafeSpeed = -MOVE_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			this.currentStrafeSpeed = MOVE_SPEED;
		}else{
			this.currentStrafeSpeed = 0;
		}
		
		if(!Mouse.isButtonDown(2)){
		 this.currentTurnSpeed = -TURN_SPEED * (Mouse.getX()-Settings.WIDTH/2);
		}
		Mouse.setCursorPosition(Settings.WIDTH/2,Settings.HEIGHT/2 );
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jump();
		}
	}

}
