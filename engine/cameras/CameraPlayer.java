package cameras;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import entities.Player;
import inputs.MouseGame;
import scene.ES;

public class CameraPlayer implements Camera { 
	
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
	
	private Player player;
	
	public boolean perspectiveMode = false;
	public boolean isUnderWater = false;
	
	//вернуть имя
	public String getName() {
		return name;
	}
	
	//конструктор
	public CameraPlayer(Player player) {
		this.player = player;
		this.name = "NoName";
	}
	
	//конструктор
	public CameraPlayer(Player player, String name) {
		this.player = player;
		this.name = name;	
	}
	
	//установить позицию
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
	
	//движение
	public void move() {
			calculateZoom();
			calculatePitchAndAngle();
			float horizontalDistance = calculateHorizontalDistance();
			float verticalDistance = calculateVerticalDistance();
			calculateCameraPosition(horizontalDistance,verticalDistance);
			this.yaw = 180 - ((player).getRotY() + angleAroundPlayer);		
	}
	
	//вернуть позицию
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
	
	//вычислить позицию камеры по горизонтали относительно игрока
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	//вычислить позицию камеры по вертикали относительно игрока
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	//вычислить масштаб бриближения камеры относительно игрока
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * ES.MOUSE_ZOOM_SPEED;
		if(((distanceFromPlayer<maxDistanceFromPlayer)&&(zoomLevel<0))
				||((distanceFromPlayer>minDistanceFromPlayer)&&(zoomLevel>0))) {
			distanceFromPlayer -= zoomLevel;
		}
	}
	
	//вычислить тангаж и угол относительно игрока
	private void calculatePitchAndAngle() {
		if(!Mouse.isButtonDown(2)) {
			float pitchChange = (Mouse.getY() - ES.DISPLAY_HEIGHT/2) * ES.MOUSE_Y_SPEED;
			
			if((pitch<maxPitch)||(pitch>minPitch)) {
				pitch -= pitchChange;
			}
		}
		
		if(MouseGame.isPressed(MouseGame.MIDDLE_CLICK)) {	
			float angleChange = (Mouse.getX() - ES.DISPLAY_WIDTH/2) * ES.MOUSE_X_SPEED;
			angleAroundPlayer = -angleChange;		
		}else{
			angleAroundPlayer = 0;
		}
	}
	
	//вычилить находится ли камера под водой
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
				
	}
	
}
