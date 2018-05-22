package object.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import control.KeyboardGame;
import control.MouseGame;
import core.Loop;
import core.settings.EngineSettings;
import object.entity.Entity;
import object.entity.Player;

public class TargetCamera extends PerspectiveCamera {

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

	private Entity target;

	public TargetCamera(String name, float fov, float nearPlane, float farPlane) {
		super(name, fov, nearPlane, farPlane);
		if (Loop.getInstance().getEditMode())
			this.angleAroundPlayer = 180;		
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}

	public void move() {
		super.move();
		if (((Player) target).isMoved()) 
			setMoved(true);
		calculateZoom();
		calculatePitchAndAngle();
		calculateOffset();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		setYaw(180 - (target.getRotation().getY() + angleAroundPlayer));
	}

	// вычислить позицию камеры относительно игрока
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = target.getRotation().getY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.setX((float) (target.getPosition().getX() - offsetX + moveOffsetX * Math.sin(target.getRotation().getY())));
		position.setZ((float) (target.getPosition().getZ() - offsetZ + moveOffsetX * Math.cos(target.getRotation().getY())));
		position.setY(target.getPosition().getY() + moveOffsetY + verticDistance);
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
			setPitch(0);;
			angleAroundPlayer = 180;
		}
		if (KeyboardGame.isKeyDown(Keyboard.KEY_UP) && moveOffsetY < maxYOffset) {
			moveOffsetY += this.offsetSpeed;
			setMoved(true);
		}
		if (KeyboardGame.isKeyDown(Keyboard.KEY_DOWN) && moveOffsetY > minYOffset) {
			moveOffsetY -= this.offsetSpeed;
			setMoved(true);
		}
		if (KeyboardGame.isKeyDown(Keyboard.KEY_LEFT) && moveOffsetX > minXOffset) {
			moveOffsetX -= this.offsetSpeed;
			setMoved(true);
		}
		if (KeyboardGame.isKeyDown(Keyboard.KEY_RIGHT) && moveOffsetX < maxXOffset) {
			moveOffsetX += offsetSpeed;
			setMoved(true);
		}
	}

	// вычислить масштаб приближения камеры относительно игрока
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * EngineSettings.MOUSE_ZOOM_SPEED;
		if (((distanceFromPlayer < maxDistanceFromPlayer) && (zoomLevel < 0))
				|| ((distanceFromPlayer > minDistanceFromPlayer) && (zoomLevel > 0))) {
			distanceFromPlayer -= zoomLevel;
			setMoved(true);
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
			setMoved(true);
	}

}
