package object.camera;

import tool.math.VMatrix4f;
import tool.math.vector.Vector3fF;

public class CubeMapCamera extends BaseCamera implements ICamera {

	/*
	 * CameraCubeMap - камера для записи кубической текстуры окружения
	 * 
	 */

	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 400f;
	private static final float FOV = 90;// don't change!
	private static final float ASPECT_RATIO = 1;

	private VMatrix4f projectionMatrix = new VMatrix4f();
	private VMatrix4f viewMatrix = new VMatrix4f();
	private VMatrix4f projectionViewMatrix = new VMatrix4f();

	public CubeMapCamera(String name, Vector3fF position) {
		super(name, position);
		this.pitch = 0;
		this.position = position;
		createProjectionMatrix();
	}
	
	@Override
	public void move() {
		// nothing
	}

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
		updateViewMatrix();
	}

	@Override
	public VMatrix4f getViewMatrix() {
		return viewMatrix;
	}

	@Override
	public VMatrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	@Override
	public VMatrix4f getProjectionViewMatrix() {
		return projectionViewMatrix;
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
		viewMatrix.rotate(new Vector3fF(0, 0, 180));
		viewMatrix.rotate(new Vector3fF(pitch, 0, 0));
		viewMatrix.rotate(new Vector3fF(0, yaw, 0));
		Vector3fF negativeCameraPos = new Vector3fF(-position.x, -position.y, -position.z);
		viewMatrix.translate(negativeCameraPos);

		projectionMatrix.mul(viewMatrix);
	}

}
