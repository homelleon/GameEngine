package object.camera;

import tool.math.Matrix4f;

public class PerspectiveCamera extends Camera {
	
	protected float fov = 1.0f;

	public PerspectiveCamera(String name, float fov, float nearPlane, float farPlane) {
		super(name);
		setFOV(fov);
		setNearPlane(nearPlane);
		setFarPlane(farPlane);
		projectionMatrix = new Matrix4f().createProjectionMatrix(fov, nearPlane, farPlane); 
	}
	
	public void setFOV(float value) {
		fov = value;
	}
	
	public float getFOV() {
		return fov;
	}

}
