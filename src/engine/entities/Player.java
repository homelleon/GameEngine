package engine.entities;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import engine.models.TexturedModel;
import engine.renderEngine.DisplayManager;
import engine.scene.Settings;
import engine.terrains.Terrain;

public class Player extends Entity {
	
	private static final float MOVE_SPEED = 20;
	private static final float RUN_SPEED = 100;
	private static final float TURN_SPEED = 80;
	private static final float JUMP_POWER = 30;
	
	private float currentForwardSpeed = 0;
	private float currentStrafeSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	public boolean isInAir = false;
	
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(List<Terrain> terrains) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float fowardDistance = currentForwardSpeed * DisplayManager.getFrameTimeSeconds();
		float strafeDistance = currentStrafeSpeed * DisplayManager.getFrameTimeSeconds();
		float speedCorrector = 1;
		
		if (fowardDistance !=0 && strafeDistance !=0) {
			speedCorrector = 2;
		}
		
		float dx = (float) (fowardDistance * Math.sin(Math.toRadians(super.getRotY())) 
				+ (strafeDistance * Math.sin(Math.toRadians(super.getRotY() + 90)))) / speedCorrector;
		float dz = (float) (fowardDistance * Math.cos(Math.toRadians(super.getRotY())) 
				+ (strafeDistance * Math.cos(Math.toRadians(super.getRotY() + 90)))) / speedCorrector;
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += Settings.GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		
		for(Terrain terrain : terrains) {
			
			if ((super.getPosition().x - terrain.getX())>=0) {
				float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
				
				if(super.getPosition().y < terrainHeight) {
					upwardsSpeed = 0;
					isInAir = false;
					super.getPosition().y = terrainHeight;
				}				
			}
		}
	}
	
	private void jump() {
		if(!isInAir) {
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}	
	}
	
	private void checkInputs() {
		if (!isInAir) {
			if((Keyboard.isKeyDown(Keyboard.KEY_W)) 
					&& (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				this.currentForwardSpeed = RUN_SPEED;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				this.currentForwardSpeed = MOVE_SPEED;			
			}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				this.currentForwardSpeed = -MOVE_SPEED; 
			}else{
				this.currentForwardSpeed = 0;	
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				this.currentStrafeSpeed = MOVE_SPEED;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				this.currentStrafeSpeed = -MOVE_SPEED;
			}else{
				this.currentStrafeSpeed = 0;
			}
		
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				jump();
			}
		}
		
		if(!Mouse.isButtonDown(2)) {
			this.currentTurnSpeed = -TURN_SPEED * (Mouse.getX()-Settings.DISPLAY_WIDTH/2) * Settings.MOUSE_X_SPEED;
			Mouse.setCursorPosition(Settings.DISPLAY_WIDTH/2,Settings.DISPLAY_HEIGHT/2);
		}			
	}
	
}
