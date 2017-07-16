package object.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class CameraCubeMap implements CameraInterface {
	
	/*
	 * CameraCubeMap - камера для записи кубической текстуры окружения
	 * 
	 */
	
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 400f;
    private static final float FOV = 90;// don't change!
    private static final float ASPECT_RATIO = 1;
	
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f projectionViewMatrix = new Matrix4f();
	
	private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 0;
	private float yaw; 
	private float roll = 0;
	
	private String name = "CubeCamera";
	
	public CameraCubeMap(Vector3f position) {
		this.position = position;	
		createProjectionMatrix();
	}

	@Override
	public void setPosition(float posX, float posY, float posZ) {
		this.position.x = posX;
		this.position.y = posY;
		this.position.z = posZ;
	}
	
	@Override
	public Vector3f getPosition() {
		return position;
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
	public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
	
	@Override
	public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
	
	@Override
    public Matrix4f getProjectionViewMatrix() {
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
 
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
    
    /**
     * Method updates current view matrix based on current camera angle and 
     * position.    
     */
    private void updateViewMatrix() {
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(180), new Vector3f(0, 0, 1), 
        		viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), 
        		viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), 
        		viewMatrix, viewMatrix);
        Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, 
        		-position.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
 
        Matrix4f.mul(projectionMatrix, viewMatrix, projectionViewMatrix);
    }
    
	@Override
	public String getName() {
		return this.name;
	}

	//установить тангаж
	@Override
	public void setPitch(float anglePitch) {
		this.pitch = anglePitch;		
	}
	
	//установить рысканье
	@Override
	public void setYaw(float angleYaw) {
		this.yaw = angleYaw;
	}

	@Override
	public void move() {
		// nothing		
	}
	
	//вернуть тангаж
	@Override
	public float getPitch() {
		return pitch;
	}
	
	//инвертировать тангаж
	@Override
	public void invertPitch() {
		this.pitch = -pitch;
	}
	
	//вернуть рыскание
	@Override
	public float getYaw() {
		return yaw;
	}
	
	//вернуть крен
	@Override
	public float getRoll() {
		return roll;
	}

	
	

}
