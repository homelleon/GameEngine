package object.camera;

import tool.math.vector.Vector3f;

public class CubeMapCamera extends Camera {

	/*
	 * CameraCubeMap - камера для записи кубической текстуры окружения
	 * 
	 */

	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 400f;
	private static final float FOV = 90;// don't change!
	private static final float ASPECT_RATIO = 1;

	public CubeMapCamera(String name) {
		super(name);
		this.pitch = 0;
		createProjectionMatrix();
	}
	
	@Override
	public void move() {
		// nothing
	}

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
		updateViewMatrix();
	}

	/**
	 * Method creates projection matrix based on field of view, aspect ratio,
	 * far and near plane clipping.
	 */
	private void createProjectionMatrix() {
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / ASPECT_RATIO;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m[0][0] = x_scale;
		projectionMatrix.m[1][1] = y_scale;
		projectionMatrix.m[2][2] = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m[2][3] = -1;
		projectionMatrix.m[3][2] = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m[3][3] = 0;
	}

	/**
	 * Method updates current view matrix based on current camera angle and
	 * position.
	 */
	private void updateViewMatrix() {
		viewMatrix.setIdentity();
		viewMatrix.rotate(new Vector3f(0, 0, 180));
		viewMatrix.rotate(new Vector3f(pitch, 0, 0));
		viewMatrix.rotate(new Vector3f(0, yaw, 0));
		Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
		viewMatrix.translate(negativeCameraPos);

		projectionMatrix.mul(viewMatrix);
	}

}
