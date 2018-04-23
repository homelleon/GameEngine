package object.entity;

import java.util.Collection;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import control.KeyboardGame;
import control.MouseGame;
import core.DisplayManager;
import core.settings.EngineSettings;
import object.terrain.Terrain;
import primitive.model.Model;
import shader.Shader;
import tool.math.vector.Vector3f;

public class Player extends Entity {

	private static final float MOVE_SPEED = 20;
	private static final float RUN_SPEED = 100;
	private static final float TURN_SPEED = 50;
	private static final float JUMP_POWER = 30;

	private float currentForwardSpeed = 0;
	private float currentStrafeSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;

	public volatile boolean isInAir = false;

	public Player(String name, Shader shader, List<Model> modelList, Vector3f position, Vector3f rotation, float scale) {
		super(name, shader, modelList, position, rotation, scale);
	}

	public void move(Collection<Terrain> terrains) {
		isMoved = false;
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float fowardDistance = currentForwardSpeed * DisplayManager.getFrameTimeSeconds();
		float strafeDistance = currentStrafeSpeed * DisplayManager.getFrameTimeSeconds();
		float speedCorrector = 1;

		if (fowardDistance != 0 && strafeDistance != 0)
			speedCorrector = 2;

		float dx = (float) (fowardDistance * Math.sin(Math.toRadians(super.getRotation().getY()))
				+ (strafeDistance * Math.sin(Math.toRadians(super.getRotation().getY() + 90)))) / speedCorrector;
		float dz = (float) (fowardDistance * Math.cos(Math.toRadians(super.getRotation().getY()))
				+ (strafeDistance * Math.cos(Math.toRadians(super.getRotation().getY() + 90)))) / speedCorrector;
		increasePosition(dx, 0, dz);
		upwardsSpeed += EngineSettings.GRAVITY * DisplayManager.getFrameTimeSeconds();
		increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);

		float terrainHeight = 0;
		for (Terrain terrain : terrains) {
			terrainHeight += terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
		}

		if (getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			getPosition().y = terrainHeight;
			isInAir = false;
		}
	}

	private void jump() {
		if (isInAir) return;
		this.upwardsSpeed = JUMP_POWER;
		isInAir = true;
	}

	private void checkInputs() {
		if (!isInAir) {
			if ((KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_MOVE_FORWARD))
					&& (KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_ACCELERATE)
							|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				currentForwardSpeed = RUN_SPEED;
				isMoved = true;
			} else if (KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_MOVE_FORWARD)) {
				currentForwardSpeed = MOVE_SPEED;
				isMoved = true;
			} else if (KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_MOVE_BACKWARD)) {
				currentForwardSpeed = -MOVE_SPEED;
				isMoved = true;
			} else {
				currentForwardSpeed = 0;
			}

			if ((KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_MOVE_LEFT))
					&& (KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_ACCELERATE)
							|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				currentStrafeSpeed = RUN_SPEED;
				isMoved = true;
			} else if ((KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_MOVE_RIGHT))
					&& (KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_ACCELERATE)
							|| Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
				currentStrafeSpeed = -RUN_SPEED;
				isMoved = true;
			} else if (KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_MOVE_LEFT)) {
				currentStrafeSpeed = MOVE_SPEED;
				isMoved = true;
			} else if (KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_MOVE_RIGHT)) {
				currentStrafeSpeed = -MOVE_SPEED;
				isMoved = true;
			} else {
				currentStrafeSpeed = 0;
			}

			if (KeyboardGame.isKeyDown(EngineSettings.KEY_PLAYER_JUMP)) {
				jump();
				isMoved = true;
			}
		}

		if (!MouseGame.isPressed(MouseGame.MIDDLE_CLICK)) {
			currentTurnSpeed = -TURN_SPEED * EngineSettings.MOUSE_X_SPEED * Mouse.getDX();
			MouseGame.centerCoursor();
		} else {
			currentTurnSpeed = 0;
		}
	}

}
