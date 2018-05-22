package object.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import core.DisplayManager;
import core.settings.EngineSettings;

public class FreeCamera extends PerspectiveCamera {

	public FreeCamera(String name, float fov, float nearPlane, float farPlane) {
		super(name, fov, nearPlane, farPlane);
	}
	
	@Override
	public void move() {
		super.move();
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
	
	
	protected void chekInputs() {
		float startRunSpeed = 1;

		if (Keyboard.isKeyDown(EngineSettings.KEY_EDITOR_ACCELERATE)) {
			runSpeed = startRunSpeed;
			isMoved = true;
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_EDITOR_MOVE_FORWARD)) {
			currentForwardSpeed = -speed * runSpeed;
			isMoved = true;
		} else if (Keyboard.isKeyDown(EngineSettings.KEY_EDITOR_MOVE_BACKWARD)) {
			currentForwardSpeed = speed * runSpeed;
			isMoved = true;
		} else {
			currentForwardSpeed = 0;
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_EDITOR_MOVE_LEFT)) {
			currentStrafeSpeed = -speed * runSpeed;
			isMoved = true;
		} else if (Keyboard.isKeyDown(EngineSettings.KEY_EDITOR_MOVE_RIGHT)) {
			currentStrafeSpeed = speed * runSpeed;
			isMoved = true;
		} else {
			currentStrafeSpeed = 0;
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_EDITOR_MOVE_UP)) {
			position.y += 0.5f;
			isMoved = true;
		}

		if (Keyboard.isKeyDown(EngineSettings.KEY_EDITOR_MOVE_DOWN)) {
			position.y -= 0.5f;
			isMoved = true;
		}
		
		if (Mouse.isButtonDown(2)) {
			this.currentTurnSpeed = speed * Mouse.getDX() * EngineSettings.MOUSE_X_SPEED * runSpeed;
			this.currentPitchSpeed = -speed * Mouse.getDY() * EngineSettings.MOUSE_Y_SPEED * runSpeed;
		} else {
			currentTurnSpeed = 0;
			currentPitchSpeed = 0;
		}
	}

}
