package object.entity.entity;

import org.lwjgl.util.vector.Vector3f;

import object.model.TexturedModel;

/**
 * 
 * @author homelleon
 * @see TexturedEntity
 */
public interface Entity {

	// ���������� ����������
	public boolean getIsVisible();

	public void setIsVisible(boolean isVisible);

	// ���������� ������ ����������
	public boolean getIsChosen();

	public void setIsChosen(boolean isChosen);

	// ���������� ��������� �������
	public float getTextureXOffset();

	public float getTextureYOffset();

	// ��������� �������
	public void increasePosition(float dx, float dy, float dz);

	public void move(float forwardSpeed, float strafeSpeed);

	// ��������� �������
	public void increaseRotation(float dx, float dy, float dz);

	// ������� ���
	public String getName();

	// ������� ���
	public int getType();

	// ���������� ���������� �������
	public TexturedModel getModel();

	/**
	 * Sets enitity textured model object.
	 * 
	 * @param model {@link TexturedModel} object
	 */
	public void setModel(TexturedModel model);

	/**
	 * Gets entity current position in 3 dimentional space coordinates.
	 *  
	 * @return {@link Vector3f} value of current position
	 */
	public Vector3f getPosition();

	public void setPosition(Vector3f position);
	
	public void setRotation(Vector3f rotation);
	
	public Vector3f getRotation();

	/**
	 * Gets entity size scale value.
	 * 
	 * @return float scale value
	 */
	public float getScale();

	/**
	 * Sets entity size scale variable.
	 * 
	 * @param scale float value
	 */
	public void setScale(float scale);

	/**
	 * Gets bounding sphere radius.
	 * 
	 * @return float value of sphere radius
	 */
	public float getSphereRadius();
	
	/**
	 * Returns clone of current entity
	 * @return {@link Entity} object
	 */
	public Entity clone(String name);

}
