package object.light;

import tool.math.vector.Vector3fF;

/*
 * Light - источник света
 * 03.02.17
 * ------------
 */

public class Light implements ILight {

	private Vector3fF position; // позиция
	private Vector3fF colour; // цвеь
	private Vector3fF attenuation = new Vector3fF(1, 0, 0); // затухание

	private String name;

	public Light(String name, Vector3fF position, Vector3fF colour) {
		this.position = position;
		this.colour = colour;
		this.name = name;
	}

	public Light(String name, Vector3fF position, Vector3fF colour, Vector3fF attenuation) {
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
		this.name = name;

	}

	public String getName() {
		return name;
	}

	// вернуть затухание
	public Vector3fF getAttenuation() {
		return attenuation;
	}

	public Vector3fF getPosition() {
		return position;
	}

	public void setPosition(Vector3fF position) {
		this.position = position;
	}

	public Vector3fF getColour() {
		return colour;
	}

	public void setColour(Vector3fF colour) {
		this.colour = colour;
	}

}
