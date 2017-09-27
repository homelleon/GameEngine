package object.camera;

import tool.math.VMatrix4f;
import tool.math.vector.Vector3fF;

public abstract class BaseCamera {
	
	protected static float speed = 100;
	protected static float runSpeed = 4;

	protected Vector3fF position = new Vector3fF(0, 0, 0);

	protected float pitch = 20;
	protected float yaw = 0;
	protected float roll;

	protected float currentForwardSpeed = 0;
	protected float currentStrafeSpeed = 0;
	protected float currentTurnSpeed = 0;
	protected float currentPitchSpeed = 0;

	protected String name;

	protected boolean isUnderWater = false;
	protected boolean isMoved = false;
	protected boolean isAngleChanged = false;

	protected BaseCamera(String name, Vector3fF position) {
		this.setPosition(position);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	// установить позицию камеры
	public void setPosition(float posX, float posY, float posZ) {
		this.position.x = posX;
		this.position.y = posY;
		this.position.z = posZ;
	}
	
	public void setPosition(Vector3fF position) {
		this.position = position;
	} 

	// установить тангаж
	public void setPitch(float anglePitch) {
		this.pitch = anglePitch;
	}

	// установить рысканье
	public void setYaw(float angleYaw) {
		this.yaw = angleYaw;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.roll += dx;
		this.yaw += dy;
		this.pitch += dz;
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public void move() {
		this.isMoved = false;
	}

	public Vector3fF getPosition() {
		return position;
	}

	// вернуть тангаж
	public float getPitch() {
		return pitch;
	}

	// инвертировать тангаж
	public void invertPitch() {
		this.pitch = -pitch;
	}

	// вернуть рысканье
	public float getYaw() {
		return yaw;
	}

	// вернуть крен
	public float getRoll() {
		return roll;
	}

	// переключить между поворотами камеры
	protected abstract void switchToFace(int faceIndex);

	protected abstract VMatrix4f getViewMatrix();

	protected abstract VMatrix4f getProjectionMatrix();
	
	protected abstract VMatrix4f getProjectionViewMatrix();

	public boolean isMoved() {
		return isMoved;
	}

	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}

	public boolean isAngleChanged() {
		return isAngleChanged;
	}

	public void setAngleChanged(boolean isAngleChanged) {
		this.isAngleChanged = isAngleChanged;
	}

}
