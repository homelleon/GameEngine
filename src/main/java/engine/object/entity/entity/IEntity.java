package object.entity.entity;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import object.Nameable;
import object.model.textured.TexturedModel;

/**
 * 
 * @author homelleon
 * @see TexturedEntity
 * @see NormalMappedEntity
 */
public interface IEntity extends Nameable {
	
	public String getBaseName();
	
	public void setBaseName(String name);
	
	/**
	 * Gets entity "visible" flag.
	 * 
	 * @return true if entity is visible<br>
	 * 		   false if entity is hidden
	 */
	public boolean getIsVisible();

	/**
	 * Sets entity "visible" flag.
	 * 
	 * @param isVisible boolean value
	 */
	public void setIsVisible(boolean isVisible);

	/**
	 * Gets entity "selected" flag.
	 * 
	 * @return true if entity is chosen<br>
	 * 		   false if entity is not chosen
	 */
	public boolean getIsChosen();

	/**
	 * Switches entity "selected" flag.
	 * 
	 * @param isChosen boolean value 
	 */
	public void setIsChosen(boolean isChosen);
	
	public Vector2f getTextureOffset();

	/**
	 * Increses entity position value in 3 dimentional space.
	 * 
	 * @param dx float value of OX plane
	 * @param dy float value of OY plane
	 * @param dz float value of OZ plane
	 */
	public void increasePosition(float dx, float dy, float dz);
	
	/**
	 * Moves entity depending on speed.
	 * 
	 * @param forwardSpeed float value of forward speed
	 * @param strafeSpeed float value of side speed
	 */
	public void move(float forwardSpeed, float strafeSpeed);

	/**
	 * Increases entity rotation value in 3 dimentional space.
	 * 
	 * @param dx float value of OX rotation plane
	 * @param dy float value of OY rotation plane
	 * @param dz float value of OZ rotation plane
	 */
	public void increaseRotation(float dx, float dy, float dz);

	/**
	 * Gets entity texture type model.
	 * 
	 * @return int value of texture type
	 */
	public int getType();

	/**
	 * Gets entity textured model object.
	 * 
	 * @return {@link TexturedModel} object
	 */
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

	/**
	 * Sets entity position in 3 dimentional space.
	 * 
	 * @param position {@link Vector3f} value in 3 dimentions
	 */
	public void setPosition(Vector3f position);
	
	/**
	 * Sets entity rotation scale in 3 dimentions.
	 * 
	 * @param rotation {@link Vector3f} value in 3 dimentions
	 */
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
	 * @return {@link IEntity} object
	 */
	public IEntity clone(String name);

}
