package object.light;

import object.GameObject;
import tool.math.vector.Vector3f;

/**
 * Light source object.
 * 
 * @author homelleon
 *
 */
public class Light extends GameObject implements ILight {

	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation = new Vector3f(0.5f, 0, 0);

	/**
	 * Light constructor without attenuation parameter.<br>
	 * Attenuation will be setted with 0.5f constant.
	 *  
	 * @param name {@link String}
	 * @param position {@link Vector3f}
	 * @param color {@link Vector3f}
	 */
	public Light(String name, Vector3f position, Vector3f color) {
		super(name);
		this.position = position;
		this.color = color;
	}
	
	/**
	 * Light constructor with attenuation parameter.<br>
	 * Attenuation is defined by Vector3f value, where:<br>
	 * x is for constant;<br>
	 * y is for linear relation;<br>
	 * z is for square relation;<br>
	 *   
	 * @param name {@link String}
	 * @param position {@link Vector3f}
	 * @param color {@link Vector3f}
	 * @param attenuation {@link Vector3f}
	 */
	public Light(String name, Vector3f position, Vector3f color, Vector3f attenuation) {
		super(name);
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;

	}
	
	@Override
	public Vector3f getAttenuation() {
		return attenuation;
	}
	
	@Override
	public Vector3f getPosition() {
		return position;
	}
	
	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@Override
	public Vector3f getColor() {
		return color;
	}

	@Override
	public void setColor(Vector3f color) {
		this.color = color;
	}

}
