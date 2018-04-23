package object.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import control.KeyboardGame;
import control.MouseGame;
import core.Loop;
import core.settings.EngineSettings;
import object.entity.Player;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public class TargetCamera extends Camera {

	/*
	 * CameraPlayer - камера для игрока
	 * 
	 */

	private final float maxDistanceFromPlayer = 100;
	private final float minDistanceFromPlayer = 0;
	private final float maxPitch = 90;
	private final float minPitch = -90;
	private final float maxYOffset = 80;
	private final float minYOffset = -10;
	private final float maxXOffset = 10;
	private final float minXOffset = -10;
	
	private final float offsetSpeed = 0.5f;

	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private float moveOffsetY = 5;
	private float moveOffsetX = 0;

	private Player player;

	public TargetCamera(String name, Player player) {
		super(name, new Vector3f(0,0,0));
		this.player = player;
		this.name = name;
		if(Loop.getInstance().getEditMode())
			this.angleAroundPlayer = 180;
	}

	@Override
	public void move() {
		super.move();
		if (player.isMoved()) 
			isMoved = true;
		calculateZoom();
		calculatePitchAndAngle();
		calculateOffset();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		yaw = 180 - (player.getRotation().getY() + angleAroundPlayer);
	}

	// вычислить позицию камеры относительно игрока
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotation().getY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = (float) (player.getPosition().x - offsetX + moveOffsetX * Math.sin(player.getRotation().getY()));
		position.z = (float) (player.getPosition().z - offsetZ + moveOffsetX * Math.cos(player.getRotation().getY()));
		position.y = player.getPosition().y + moveOffsetY + verticDistance;
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateOffset() {
		if (!Loop.getInstance().getEditMode()) return;
		if (MouseGame.isPressed(MouseGame.RIGHT_CLICK)) {
			moveOffsetX = 0;
			moveOffsetY = 0;
			pitch = 0;
			angleAroundPlayer = 180;
		}
		if (KeyboardGame.isKeyDown(Keyboard.KEY_UP) && moveOffsetY < maxYOffset) {
			moveOffsetY += this.offsetSpeed;
			isMoved = true;
		}
		if (KeyboardGame.isKeyDown(Keyboard.KEY_DOWN) && moveOffsetY > minYOffset) {
			moveOffsetY -= this.offsetSpeed;
			isMoved = true;
		}
		if (KeyboardGame.isKeyDown(Keyboard.KEY_LEFT) && moveOffsetX > minXOffset) {
			moveOffsetX -= this.offsetSpeed;
			isMoved = true;
		}
		if (KeyboardGame.isKeyDown(Keyboard.KEY_RIGHT) && moveOffsetX < maxXOffset) {
			moveOffsetX += offsetSpeed;
			isMoved = true;
		}
	}

	// вычислить масштаб приближения камеры относительно игрока
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * EngineSettings.MOUSE_ZOOM_SPEED;
		if (((distanceFromPlayer < maxDistanceFromPlayer) && (zoomLevel < 0))
				|| ((distanceFromPlayer > minDistanceFromPlayer) && (zoomLevel > 0))) {
			distanceFromPlayer -= zoomLevel;
			isMoved = true;
		}
	}

	private void calculatePitchAndAngle() {
		float pitchChange = 0;
		float angleChange = 0;
		if ((!Loop.getInstance().getEditMode() && !MouseGame.isPressed(MouseGame.MIDDLE_CLICK))||
				(Loop.getInstance().getEditMode() && MouseGame.isPressed(MouseGame.MIDDLE_CLICK))) {
			pitchChange = Mouse.getDY() * EngineSettings.MOUSE_Y_SPEED;

			if ((pitch < maxPitch) || (pitch > minPitch))
				pitch -= pitchChange;
		}

		if (MouseGame.isPressed(MouseGame.MIDDLE_CLICK)) {
			angleChange = Mouse.getDX() * EngineSettings.MOUSE_X_SPEED;
			angleAroundPlayer += -angleChange;
		} else if (!Loop.getInstance().getEditMode()) {
			angleAroundPlayer = 0;
		}
		if ((pitchChange != 0) || (angleChange !=0)) 
			isMoved = true;
	}

	@SuppressWarnings("unused")
	private void underWaterCalculate() {
		isUnderWater = (position.y <= 0);
	}

	@Override
	public Matrix4f getViewMatrix() {
		return Maths.createViewMatrix(this);
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return null;
	}

	@Override
	public Matrix4f getProjectionViewMatrix() {
		return null;
	}

	@Override
	public void switchToFace(int faceIndex) {
		// do nothing
		
	}

}
