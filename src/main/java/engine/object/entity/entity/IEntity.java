package object.entity.entity;

import object.Nameable;
import object.model.textured.TexturedModel;
import tool.math.vector.Vec2f;
import tool.math.vector.Vec3f;

/**
 * 
 * @author homelleon
 * @see TexturedEntity
 * @see NormalMappedEntity
 */
 public interface IEntity extends Nameable {
	
	 String getBaseName();
	
	 void setBaseName(String name);
	
	/**
	 * Gets entity "visible" flag.
	 * 
	 * @return true if entity is visible<br>
	 * 		   false if entity is hidden
	 */
	 boolean isVisible();

	/**
	 * Sets entity "visible" flag.
	 * 
	 * @param isVisible boolean value
	 */
	 void setVisible(boolean isVisible);

	/**
	 * Gets entity "selected" flag.
	 * 
	 * @return true if entity is chosen<br>
	 * 		   false if entity is not chosen
	 */
	 boolean isChosen();

	/**
	 * Switches entity "selected" flag.
	 * 
	 * @param isChosen boolean value 
	 */
	 void setChosen(boolean isChosen);
	
	 Vec2f getTextureOffset();

	/**
	 * Increses entity position value in 3 dimentional space.
	 * 
	 * @param dx float value of OX plane
	 * @param dy float value of OY plane
	 * @param dz float value of OZ plane
	 */
	 void increasePosition(float dx, float dy, float dz);
	
	/**
	 * Moves entity depending on speed.
	 * 
	 * @param forwardSpeed float value of forward speed
	 * @param strafeSpeed float value of side speed
	 */
	 void move(float forwardSpeed, float strafeSpeed);

	/**
	 * Increases entity rotation value in 3 dimentional space.
	 * 
	 * @param dx float value of OX rotation plane
	 * @param dy float value of OY rotation plane
	 * @param dz float value of OZ rotation plane
	 */
	 void increaseRotation(float dx, float dy, float dz);

	/**
	 * Gets entity texture type model.
	 * 
	 * @return int value of texture type
	 */
	 int getType();

	/**
	 * Gets entity textured model object.
	 * 
	 * @return {@link TexturedModel} object
	 */
	 TexturedModel getModel();

	/**
	 * Sets enitity textured model object.
	 * 
	 * @param model {@link TexturedModel} object
	 */
	 void setModel(TexturedModel model);

	/**
	 * Gets entity current position in 3 dimentional space coordinates.
	 *  
	 * @return {@link Vec3f} value of current position
	 */
	 Vec3f getPosition();

	/**
	 * Sets entity position in 3 dimentional space.
	 * 
	 * @param position {@link Vec3f} value in 3 dimentions
	 */
	 void setPosition(Vec3f position);
	
	/**
	 * Sets entity rotation scale in 3 dimentions.
	 * 
	 * @param rotation {@link Vec3f} value in 3 dimentions
	 */
	 void setRotation(Vec3f rotation);
	
	 Vec3f getRotation();

	/**
	 * Gets entity size scale value.
	 * 
	 * @return float scale value
	 */
	 float getScale();

	/**
	 * Sets entity size scale variable.
	 * 
	 * @param scale float value
	 */
	 void setScale(float scale);

	/**
	 * Gets bounding sphere radius.
	 * 
	 * @return float value of sphere radius
	 */
	 float getSphereRadius();
	
	/**
	 * Returns clone of current entity
	 * @return {@link IEntity} object
	 */
	 IEntity clone(String name);
	
	 boolean isMoved();

	 void setMoved(boolean isMoved);

}
