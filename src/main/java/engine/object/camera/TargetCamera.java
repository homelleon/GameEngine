package object.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.loop.Loop;
import core.settings.EngineSettings;
import object.entity.player.IPlayer;
import object.input.KeyboardGame;
import object.input.MouseGame;

public class TargetCamera implements ICamera {

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

	private Vector3f position = new Vector3f(0, 0, 0);

	private float pitch = 20;
	private float yaw = 0;
	private float roll;
	
	private float moveOffsetY = 0;
	private float moveOffsetX = 0;

	private String name;

	private IPlayer player;

	public boolean isUnderWater = false;

	@Override
	public String getName() {
		return name;
	}

	public TargetCamera(IPlayer player, String name) {
		this.player = player;
		this.name = name;
		if(Loop.getInstance().getEditMode()) {
			this.angleAroundPlayer = 180;
		}
	}

	@Override
	public void setPosition(float posX, float posY, float posZ) {
		this.position.x = posX;
		this.position.y = posY;
		this.position.z = posZ;
	}

	// установить тангаж
	@Override
	public void setPitch(float anglePitch) {
		this.pitch = anglePitch;
	}

	// установить рысканье
	@Override
	public void setYaw(float angleYaw) {
		this.yaw = angleYaw;
	}

	@Override
	public void move() {
		calculateZoom();
		calculatePitchAndAngle();
		calculateOffset();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotation().getY() + angleAroundPlayer);
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	// вернуть тангаж
	@Override
	public float getPitch() {
		return pitch;
	}

	// инвертировать тангаж
	@Override
	public void invertPitch() {
		this.pitch = -pitch;
	}

	// вернуть рысканье
	@Override
	public float getYaw() {
		return yaw;
	}

	// вернуть крен
	@Override
	public float getRoll() {
		return roll;
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
		if(Loop.getInstance().getEditMode()) {
			if(MouseGame.isPressed(MouseGame.RIGHT_CLICK)) {
				this.moveOffsetX = 0;
				this.moveOffsetY = 0;
				this.pitch = 0;
				this.angleAroundPlayer = 180;
			}
			if(KeyboardGame.isKeyDown(Keyboard.KEY_UP) && this.moveOffsetY < maxYOffset) {
				this.moveOffsetY += this.offsetSpeed;
			}
			if(KeyboardGame.isKeyDown(Keyboard.KEY_DOWN) && this.moveOffsetY > minYOffset) {
				this.moveOffsetY -= this.offsetSpeed;
			}
			if(KeyboardGame.isKeyDown(Keyboard.KEY_LEFT) && this.moveOffsetX > minXOffset) {
				this.moveOffsetX -= this.offsetSpeed;
			}
			if(KeyboardGame.isKeyDown(Keyboard.KEY_RIGHT) && this.moveOffsetX < maxXOffset) {
				this.moveOffsetX += this.offsetSpeed;
			}
		}
	}

	// вычислить масштаб приближения камеры относительно игрока
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * EngineSettings.MOUSE_ZOOM_SPEED;
		if (((distanceFromPlayer < maxDistanceFromPlayer) && (zoomLevel < 0))
				|| ((distanceFromPlayer > minDistanceFromPlayer) && (zoomLevel > 0))) {
			distanceFromPlayer -= zoomLevel;
		}
	}

	private void calculatePitchAndAngle() {
		if ((!Loop.getInstance().getEditMode() && !MouseGame.isPressed(MouseGame.MIDDLE_CLICK))||
				(Loop.getInstance().getEditMode() && MouseGame.isPressed(MouseGame.MIDDLE_CLICK))) {
			float pitchChange = Mouse.getDY() * EngineSettings.MOUSE_Y_SPEED;

			if ((pitch < maxPitch) || (pitch > minPitch)) {
				pitch -= pitchChange;
			}
		}

		if (MouseGame.isPressed(MouseGame.MIDDLE_CLICK)) {
			float angleChange = Mouse.getDX() * EngineSettings.MOUSE_X_SPEED;
			angleAroundPlayer += -angleChange;
		} else if(!Loop.getInstance().getEditMode()) {
			angleAroundPlayer = 0;
		}
	}

	@SuppressWarnings("unused")
	private void underWaterCalculate() {
		if (this.position.y <= 0) {
			isUnderWater = true;
		} else {
			isUnderWater = false;
		}
	}

	// переключить повороты камеры
	@Override
	public void switchToFace(int faceIndex) {
		switch (faceIndex) {
		case 0:
			pitch = 0;
			yaw = 90;
			break;
		case 1:
			pitch = 0;
			yaw = -90;
			break;
		case 2:
			pitch = -90;
			yaw = 180;
			break;
		case 3:
			pitch = 90;
			yaw = 180;
			break;
		case 4:
			pitch = 0;
			yaw = 180;
			break;
		case 5:
			pitch = 0;
			yaw = 0;
			break;
		}
	}

	@Override
	public Matrix4f getViewMatrix() {
		return null;
	}

	@Override
	public Matrix4f getProjectionMatrix() {
		return null;
	}

	@Override
	public Matrix4f getProjectionViewMatrix() {
		return null;
	}

}
