package cameras;

import org.lwjgl.util.vector.Vector3f;

public interface Camera {
	 
	public String getName();  //вернуть имя
	public void setPosition(float posX, float posY, float posZ); //установить позицию
	public void setPitch(float anglePitch); //установить тангаж камеры
	public void setYaw(float angleYaw); // установить рысканье камеры
	public void move(); //движение камеры
	public Vector3f getPosition(); //вернуть позицию камеры
	public float getPitch(); //вернуть тангаж камеры
	public void invertPitch(); //инвертировать тангаж камеры
	public float getYaw(); //вернуть рысканье камеры
	public float getRoll(); //вернуть крен камеры
	public void switchToFace(int faceIndex); //переключить повороты камеры
}
