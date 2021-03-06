package object.camera;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.entity.player.IPlayer;
import object.input.MouseGame;

public class TargetCamera implements ICamera {

	/*
	 * CameraPlayer - ������ ��� ������
	 * 
	 */

	private static final float maxDistanceFromPlayer = 100;
	private static final float minDistanceFromPlayer = 0;
	private static final float maxPitch = 90;
	private static final float minPitch = -90;

	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(0, 0, 0);

	private float pitch = 20;
	private float yaw = 0;
	private float roll;

	private String name;

	private IPlayer player;

	public boolean perspectiveMode = false;
	public boolean isUnderWater = false;

	@Override
	public String getName() {
		return name;
	}

	public TargetCamera(IPlayer player) {
		this.player = player;
		this.name = "NoName";
	}

	public TargetCamera(IPlayer player, String name) {
		this.player = player;
		this.name = name;
	}

	@Override
	public void setPosition(float posX, float posY, float posZ) {
		this.position.x = posX;
		this.position.y = posY;
		this.position.z = posZ;
	}

	// ���������� ������
	@Override
	public void setPitch(float anglePitch) {
		this.pitch = anglePitch;
	}

	// ���������� ��������
	@Override
	public void setYaw(float angleYaw) {
		this.yaw = angleYaw;
	}

	@Override
	public void move() {
		calculateZoom();
		calculatePitchAndAngle();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - ((player).getRotation().getY() + angleAroundPlayer);
	}

	@Override
	public Vector3f getPosition() {
		return position;
	}

	// ������� ������
	@Override
	public float getPitch() {
		return pitch;
	}

	// ������������� ������
	@Override
	public void invertPitch() {
		this.pitch = -pitch;
	}

	// ������� ��������
	@Override
	public float getYaw() {
		return yaw;
	}

	// ������� ����
	@Override
	public float getRoll() {
		return roll;
	}

	// ��������� ������� ������ ������������ ������
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotation().getY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + 5 + verticDistance;
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	// ��������� ������� ����������� ������ ������������ ������
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * EngineSettings.MOUSE_ZOOM_SPEED;
		if (((distanceFromPlayer < maxDistanceFromPlayer) && (zoomLevel < 0))
				|| ((distanceFromPlayer > minDistanceFromPlayer) && (zoomLevel > 0))) {
			distanceFromPlayer -= zoomLevel;
		}
	}

	private void calculatePitchAndAngle() {
		if (!Mouse.isButtonDown(2)) {
			float pitchChange = (Mouse.getY() - EngineSettings.DISPLAY_HEIGHT / 2) * EngineSettings.MOUSE_Y_SPEED;

			if ((pitch < maxPitch) || (pitch > minPitch)) {
				pitch -= pitchChange;
			}
		}

		if (MouseGame.isPressed(MouseGame.MIDDLE_CLICK)) {
			float angleChange = (Mouse.getX() - EngineSettings.DISPLAY_WIDTH / 2) * EngineSettings.MOUSE_X_SPEED;
			angleAroundPlayer = -angleChange;
		} else {
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

	// ����������� �������� ������
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
