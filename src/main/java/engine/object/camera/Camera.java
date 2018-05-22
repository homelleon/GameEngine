package object.camera;

import tool.math.Frustum;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector3f;

public abstract class Camera {
	
	protected static float speed = 100;
	protected static float runSpeed = 4;

	protected Vector3f position = new Vector3f(0, 0, 0);

	protected float pitch = 20;
	protected float yaw = 0;
	protected float roll;
	
	protected float nearPlane = 1.0f;
	protected float farPlane = 10.0f;
	
	protected Matrix4f projectionMatrix = new Matrix4f();
	protected Matrix4f viewMatrix = new Matrix4f();

	protected float currentForwardSpeed = 0;
	protected float currentStrafeSpeed = 0;
	protected float currentTurnSpeed = 0;
	protected float currentPitchSpeed = 0;
	
	protected Frustum frustum;

	protected String name;

	protected boolean isUnderWater = false;
	protected boolean isMoved = true;

	protected Camera(String name) {
		this.frustum = new Frustum();
		this.name = name;
		viewMatrix = Maths.createViewMatrix(this);
	}
	
	public String getName() {
		return name;
	}
	
	public void setNearPlane(float value) {
		nearPlane = value;
	}
	
	public float getNearPlane() {
		return nearPlane;
	}
	
	public void setFarPlane(float value) {
		farPlane = value;
	}
	
	public float getFarPlane() {
		return farPlane;
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

	public Matrix4f getViewMatrix() {
		return Maths.createViewMatrix(this);
	};

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix.clone();
	};
	
	public Matrix4f getProjectionViewMatrix() {
		return Matrix4f.mul(getProjectionMatrix(), getViewMatrix());
	};

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
