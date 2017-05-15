package cameras;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Class that allows to set a world camera.
 * 
 * @author homelleon
 * 
 * @see CameraFree
 * @see CameraPlayer
 * @see CameraCubeMap
 *
 */

//TODO: need two interfaces for camera and cube camera

public interface Camera {
	
	/**
	 * 
	 * @return String vlaue of camera name
	 */	
	public String getName();  //������� ���
	/**
	 * Method sets current camera position in 3 dimentional space.
	 * 
	 * @param posX
	 * 				float value of x-coordinate of the camera
	 * @param posY
	 * 				float value of y-coordinate of the camera
	 * @param posZ
	 * 				float value of z-coordinate of the camera
	 */
	public void setPosition(float posX, float posY, float posZ); //���������� �������
	
	/**
	 * Method sets pitch angle of the camera.
	 * 
	 * @param anglePitch
	 * 					 float value of pitch angle 
	 */
	public void setPitch(float anglePitch); //���������� ������ ������
	
	/**
	 * Method sets yaw angle of the camera.
	 * 
	 * @param angleYaw
	 * 					float value of yaw angle
	 */
	public void setYaw(float angleYaw); // ���������� �������� ������
	
	/**
	 * Method that starts movement calculation.
	 */
	public void move(); //�������� ������
	
	/**
	 * Method returns current position of camera in 3 dimentional space.
	 * 
	 * @return Vector3f
	 * 					value of current position
	 */
	public Vector3f getPosition(); //������� ������� ������
	
	/**
	 * Method returns current pitch angle of the camera.
	 * 
	 * @return float
	 * 				 value of current pitch angle
	 */
	public float getPitch(); //������� ������ ������
	
	/**
	 * Method invert pitch angle to opposite.
	 */
	public void invertPitch(); //������������� ������ ������
	
	/**
	 * Method returns current yaw angle of the camera.
	 * 
	 * @return float
	 * 				 value of current yaw angle
	 */
	public float getYaw(); //������� �������� ������
	
	/**
	 * Method returns current roll angle of the camera.
	 * 
	 * @return float
	 * 				 value of current roll angle
	 */
	public float getRoll(); //������� ���� ������
	
	/**
	 * Method switch between different angle faces changing pitch and yaw 
	 * angle.
	 * 
	 * @param faceIndex
	 * 					int value of the face count number
	 */
	public void switchToFace(int faceIndex); //����������� �������� ������
	
	/**
	 * 
	 * @return Matrix4f
	 * 					value of current view matrix
	 */
	public Matrix4f getViewMatrix();
	
	/**
	 * 
	 * @return Matrix4f
	 * 					value of current projection matrix
	 */
	public Matrix4f getProjectionMatrix();
	
	/**
	 * 
	 * @return Matrix4f
	 * 					value of current projection-view matrix
	 */
	public Matrix4f getProjectionViewMatrix();
}
