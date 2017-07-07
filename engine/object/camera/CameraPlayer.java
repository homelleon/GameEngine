package object.camera;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import core.settings.EngineSettings;
import object.entity.player.PlayerInterface;
import object.input.MouseGame;

public class CameraPlayer implements CameraInterface { 
	
	/*
	 * CameraPlayer - камера для игрока
	 * 
	 */
	
	private static final float maxDistanceFromPlayer = 100;
	private static final float minDistanceFromPlayer = 0;
	private static final float maxPitch = 90;
	private static final float minPitch = -90;
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 20; 
	private float yaw = 0;
	private float roll;
	
	private String name;
	
	private PlayerInterface player;
	
	public boolean perspectiveMode = false;
	public boolean isUnderWater = false;
	
	public String getName() {
		return name;
	}
	
	public CameraPlayer(PlayerInterface player) {
		this.player = player;
		this.name = "NoName";
	}
	
	public CameraPlayer(PlayerInterface player, String name) {
		this.player = player;
		this.name = name;	
	}
	
	public void setPosition(float posX, float posY, float posZ) {
		this.position.x = posX;
		this.position.y = posY;
		this.position.z = posZ;
	}
	
	//установить тангаж
	public void setPitch(float anglePitch) {
		this.pitch = anglePitch;
	}
	
	//установить рысканье
	public void setYaw(float angleYaw) {
		this.yaw = angleYaw;
	}
	
	public void move() {
			calculateZoom();
			calculatePitchAndAngle();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCameraPosition(horizontalDistance,verticalDistance);
			this.yaw = 180 - ((player).getRotY() + angleAroundPlayer);		
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	//вернуть тангаж
	public float getPitch() {
		return pitch;
	}
	
	//инвертировать тангаж
	public void invertPitch() {
		this.pitch = -pitch;
	}
	
	//вернуть рысканье
	public float getYaw() {
		return yaw;
	}
	
	//вернуть крен
	public float getRoll() {
		return roll;
	}
	
	//вычислить позицию камеры относительно игрока
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
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
	
	//вычислить масштаб приближения камеры относительно игрока
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * EngineSettings.MOUSE_ZOOM_SPEED;
		if(((distanceFromPlayer<maxDistanceFromPlayer)&&(zoomLevel<0))
				||((distanceFromPlayer>minDistanceFromPlayer)&&(zoomLevel>0))) {
			distanceFromPlayer -= zoomLevel;
		}
	}
	
	private void calculatePitchAndAngle() {
		if(!Mouse.isButtonDown(2)) {
			float pitchChange = (Mouse.getY() - EngineSettings.DISPLAY_HEIGHT/2) * EngineSettings.MOUSE_Y_SPEED;
			
			if((pitch<maxPitch)||(pitch>minPitch)) {
				pitch -= pitchChange;
			}
		}
		
		if(MouseGame.isPressed(MouseGame.MIDDLE_CLICK)) {	
			float angleChange = (Mouse.getX() - EngineSettings.DISPLAY_WIDTH/2) * EngineSettings.MOUSE_X_SPEED;
			angleAroundPlayer = -angleChange;		
		}else{
			angleAroundPlayer = 0;
		}
	}
	
	@SuppressWarnings("unused")
	private void underWaterCalculate() {
		if(this.position.y <=0) {
			isUnderWater = true;
		} else {
			isUnderWater = false;
		}
	}
	
	//переключить повороты камеры
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
