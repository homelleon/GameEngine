package object.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import core.display.DisplayManager;
import core.settings.EngineSettings;
import tool.math.VMatrix4f;
import tool.math.vector.Vector3fF;

public class FreeCamera extends BaseCamera implements ICamera {

	public FreeCamera(String name, Vector3fF position) {
		super(name, position);
		this.setPosition(position);
		this.name = name;
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


	@Override
	public VMatrix4f getViewMatrix() {
		return null;
	}

	@Override
	public VMatrix4f getProjectionMatrix() {
		return null;
	}

	@Override
	public VMatrix4f getProjectionViewMatrix() {
		return null;
	}

	@Override
	public void switchToFace(int faceIndex) {
		//do nothing		
	}

}
