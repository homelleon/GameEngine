package object.camera;

import tool.math.Frustum;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public abstract class Camera {
	
	protected static float speed = 100;
	protected static float runSpeed = 4;

	protected Vector3f position = new Vector3f(0, 0, 0);

	protected float pitch = 20;
	protected float yaw = 0;
	protected float roll;

	protected float currentForwardSpeed = 0;
	protected float currentStrafeSpeed = 0;
	protected float currentTurnSpeed = 0;
	protected float currentPitchSpeed = 0;
	
	protected Frustum frustum;

	protected String name;

	protected boolean isUnderWater = false;
	protected boolean isMoved = true;

	protected Camera(String name, Vector3f position) {
		this.frustum = new Frustum();
		setPosition(position);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	// установить позицию камеры
	public void setPosition(float posX, float posY, float posZ) {
		position.x = posX;
		position.y = posY;
		position.z = posZ;
	}
	
	public void setPosition(Vector3f position) {
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
		roll += dx;
		yaw += dy;
		pitch += dz;
	}

	public void increasePosition(float dx, float dy, float dz) {
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}

	public void move() {
		isMoved = false;
	}

	public Vector3f getPosition() {
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
	public abstract void switchToFace(int faceIndex);

	public abstract Matrix4f getViewMatrix();

	public abstract Matrix4f getProjectionMatrix();
	
	public abstract Matrix4f getProjectionViewMatrix();

	public boolean isMoved() {
		return isMoved;
	}

	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}

	public Frustum getFrustum() {
		return frustum;
	}

}
