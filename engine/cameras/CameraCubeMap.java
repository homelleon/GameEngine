package cameras;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class CameraCubeMap implements Camera {
	
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 200f;
    private static final float FOV = 90;// don't change!
    private static final float ASPECT_RATIO = 1;
	
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f viewMatrix = new Matrix4f();
	private Matrix4f projectionViewMatrix = new Matrix4f();
	
	private Vector3f position = new Vector3f(0,0,0);
	
	private float pitch = 0;
	private float yaw; 
	private float roll;
	
	public CameraCubeMap(Vector3f position) {
		this.position = position;	
		createProjectionMatrix();
	}
	
	public void setPosition(float posX, float posY, float posZ) {
		this.position.x = posX;
		this.position.y = posY;
		this.position.z = posZ;
	}
	
	public Vector3f getPosition() {
		return position;
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

	public void switchToFaced(int faceIndex) {
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
	
	public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
	
	public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
 
    public Matrix4f getProjectionViewMatrix() {
        return projectionViewMatrix;
    }
 
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
 
    private void updateViewMatrix() {
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(180), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f negativeCameraPos = new Vector3f(-position.x, -position.y, -position.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
 
        Matrix4f.mul(projectionMatrix, viewMatrix, projectionViewMatrix);
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPitch(float anglePitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setYaw(float angleYaw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void invertPitch() {
		this.pitch = -pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	
	

}
