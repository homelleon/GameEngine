package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

/**
 * 
 * @author homelleon
 * @see Entity
 */
public interface EntityInterface {
	
	//управление видимостью
	public boolean getIsVisible();
	public void setIsVisible(boolean isVisible);
	
	//управление выбора указателем
	public boolean getIsChosen();
	public void setIsChosen(boolean isChosen);

	//управление смещением текстур
	public float getTextureXOffset();	
	public float getTextureYOffset();
	
	//увеличить позицию
	public void increasePosition(float dx, float dy, float dz);
	public void move(float forwardSpeed, float strafeSpeed);
	//увеличить поворот
	public void increaseRotation(float dx, float dy, float dz);
	
	//вернуть имя
	public String getName();
	//вернуть тип
	public int getType();
	
	//управление текстурной моделью
	public TexturedModel getModel();
	public void setModel(TexturedModel model);
	
	//управление позицией
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	
	//управление поворотом
	public float getRotX();
	public void setRotX(float rotX);
	public float getRotY();
	public void setRotY(float rotY);
	public float getRotZ();
	public void setRotZ(float rotZ);
	
	//управление масштабом 
	public float getScale();
	public void setScale(float scale);
	
	//вернуть радиус ограничивающей сферы
	public float getSphereRadius();
	
	
}
