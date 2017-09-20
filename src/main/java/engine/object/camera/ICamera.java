package object.camera;

import tool.math.Matrix4f;
import tool.math.vector.Vec3f;

/**
 * Class that allows to set a world camera.
 * 
 * @author homelleon
 * 
 * @see FreeCamera
 * @see TargetCamera
 * @see CubeMapCamera
 *
 */

// TODO: need two interfaces for camera and cube camera

 public interface ICamera {

	/**
	 * 
	 * @return String vlaue of camera name
	 */
	 String getName(); // вернуть имя

	/**
	 * Method sets current camera position in 3 dimentional space.
	 * 
	 * @param posX
	 *            float value of x-coordinate of the camera
	 * @param posY
	 *            float value of y-coordinate of the camera
	 * @param posZ
	 *            float value of z-coordinate of the camera
	 */
	 void setPosition(float posX, float posY, float posZ); // установить
																	// позицию

	/**
	 * Method sets pitch angle of the camera.
	 * 
	 * @param anglePitch
	 *            float value of pitch angle
	 */
	 void setPitch(float anglePitch); // установить тангаж камеры

	/**
	 * Method sets yaw angle of the camera.
	 * 
	 * @param angleYaw
	 *            float value of yaw angle
	 */
	 void setYaw(float angleYaw); // установить рысканье камеры

	/**
	 * Method that starts movement calculation.
	 */
	 void move(); // движение камеры

	/**
	 * Method returns current position of camera in 3 dimentional space.
	 * 
	 * @return Vec3f value of current position
	 */
	 Vec3f getPosition(); // вернуть позицию камеры

	/**
	 * Method returns current pitch angle of the camera.
	 * 
	 * @return float value of current pitch angle
	 */
	 float getPitch(); // вернуть тангаж камеры

	/**
	 * Method invert pitch angle to opposite.
	 */
	 void invertPitch(); // инвертировать тангаж камеры

	/**
	 * Method returns current yaw angle of the camera.
	 * 
	 * @return float value of current yaw angle
	 */
	 float getYaw(); // вернуть рысканье камеры

	/**
	 * Method returns current roll angle of the camera.
	 * 
	 * @return float value of current roll angle
	 */
	 float getRoll(); // вернуть крен камеры
	
	 boolean isMoved();

	 void setMoved(boolean isMoved);

	 boolean isAngleChanged();

	 void setAngleChanged(boolean isAngleChanged);

	/**
	 * Method switch between different angle faces changing pitch and yaw angle.
	 * 
	 * @param faceIndex
	 *            int value of the face count number
	 */
	 void switchToFace(int faceIndex); // переключить повороты камеры

	/**
	 * 
	 * @return Matrix4f value of current view matrix
	 */
	 Matrix4f getViewMatrix();

	/**
	 * 
	 * @return Matrix4f value of current projection matrix
	 */
	 Matrix4f getProjectionMatrix();

	/**
	 * 
	 * @return Matrix4f value of current projection-view matrix
	 */
	 Matrix4f getProjectionViewMatrix();
}
