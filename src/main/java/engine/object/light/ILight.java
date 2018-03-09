package object.light;

import object.Nameable;
import tool.math.vector.Color;
import tool.math.vector.Vector3f;

/**
 * Light source interface.
 * 
 * @author homelleon
 * @version 1.0
 * @see Light
 */
public interface ILight extends Nameable {
	/**
	 * Gets 3 type of attenuation value.<br>
	 * Use x for constant attenuation.<br> 
	 * Use y for linear attenuation.<br>
	 * Use z for square attenuation.<br>
	 *
	 * @return {@link Vector3f} value
	 */
	public Vector3f getAttenuation();
	
	/**
	 * Gets position of current light source.
	 * 
	 * @return {@link Vector3f} value
	 */
	public Vector3f getPosition();
	
	/**
	 * Sets current light source position.
	 * 
	 * @param position {@link Vector3f} value
	 */
	public void setPosition(Vector3f position);
	
	/**
	 * Gets current light color value.
	 * 
	 * @return {@link Color} value
	 */
	public Color getColor();
	
	/**
	 * Sets color for current light source.
	 * 
	 * @param color {@link Color} value
	 */
	public void setColor(Color color);

}
