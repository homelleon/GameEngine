package object.light;

import object.GameObject;
import tool.math.vector.Vector3f;

/*
 * Light - источник света
 * 03.02.17
 * ------------
 */

public class Light extends GameObject implements ILight {

	private Vector3f position; // позиция
	private Vector3f color; // цвеь
	private Vector3f attenuation = new Vector3f(1, 0, 0); // затухание

	private String name;

	public Light(String name, Vector3f position, Vector3f colour) {
		super(name);
		this.position = position;
		this.color = colour;
	}

	public Light(String name, Vector3f position, Vector3f colour, Vector3f attenuation) {
		super(name);
		this.position = position;
		this.color = colour;
		this.attenuation = attenuation;

	}

	// вернуть затухание
	public Vector3f getAttenuation() {
		return attenuation;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

}
