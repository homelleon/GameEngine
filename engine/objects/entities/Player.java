package objects.entities;

import java.util.Collection;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import core.display.DisplayManager;
import core.settings.ES;
import inputs.KeyboardGame;
import inputs.MouseGame;
import objects.models.TexturedModel;
import objects.terrains.TerrainInterface;

public class Player extends Entity implements PlayerInterface {
	
	private static final float MOVE_SPEED = 20;
	private static final float RUN_SPEED = 100;
	private static final float TURN_SPEED = 80;
	private static final float JUMP_POWER = 30;
	
	private float currentForwardSpeed = 0;
	private float currentStrafeSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	public boolean isInAir = false;
	
	
	public Player(String name, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(name, model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(Collection<TerrainInterface> terrains) {
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
		upwardsSpeed += ES.GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		
		float terrainHeight = 0; 
		for(TerrainInterface terrain : terrains) {		
			terrainHeight += terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);								
		}
		
		if(super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
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
			if((KeyboardGame.isKeyDown(ES.KEY_PLAYER_MOVE_FORWARD)) 
					&& (KeyboardGame.isKeyDown(ES.KEY_PLAYER_ACCELERATE) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				this.currentForwardSpeed = RUN_SPEED;
			}else if(KeyboardGame.isKeyDown(ES.KEY_PLAYER_MOVE_FORWARD)) {
				this.currentForwardSpeed = MOVE_SPEED;			
			}else if(KeyboardGame.isKeyDown(ES.KEY_PLAYER_MOVE_BACKWARD)) {
				this.currentForwardSpeed = -MOVE_SPEED; 
			}else{
				this.currentForwardSpeed = 0;	
			}
			
			if((KeyboardGame.isKeyDown(ES.KEY_PLAYER_MOVE_LEFT)) 
					&& (KeyboardGame.isKeyDown(ES.KEY_PLAYER_ACCELERATE) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				this.currentStrafeSpeed = RUN_SPEED;
			} else if ((KeyboardGame.isKeyDown(ES.KEY_PLAYER_MOVE_RIGHT)) 
					&& (KeyboardGame.isKeyDown(ES.KEY_PLAYER_ACCELERATE) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				this.currentStrafeSpeed = -RUN_SPEED;
			} else if(KeyboardGame.isKeyDown(ES.KEY_PLAYER_MOVE_LEFT)) {
				this.currentStrafeSpeed = MOVE_SPEED;
			}else if(KeyboardGame.isKeyDown(ES.KEY_PLAYER_MOVE_RIGHT)) {
				this.currentStrafeSpeed = -MOVE_SPEED;
			}else{
				this.currentStrafeSpeed = 0;
			}
		
			if(KeyboardGame.isKeyDown(ES.KEY_PLAYER_JUMP)) { 
				jump();
			}
		}
		
		if(!MouseGame.isPressed(MouseGame.MIDDLE_CLICK)) {
			this.currentTurnSpeed = -TURN_SPEED * (Mouse.getX() - ES.DISPLAY_WIDTH / 2) * ES.MOUSE_X_SPEED;
			Mouse.setCursorPosition(ES.DISPLAY_WIDTH / 2, ES.DISPLAY_HEIGHT / 2);
		}			
	}
	
}
