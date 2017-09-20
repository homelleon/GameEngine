package object.light;

import tool.math.vector.Vec3f;

/*
 * Light - источник света
 * 03.02.17
 * ------------
 */

public class Light implements ILight {

	private Vec3f position; // позиция
	private Vec3f colour; // цвеь
	private Vec3f attenuation = new Vec3f(1, 0, 0); // затухание

	private String name;

	public Light(String name, Vec3f position, Vec3f colour) {
		this.position = position;
		this.colour = colour;
		this.name = name;
	}

	public Light(String name, Vec3f position, Vec3f colour, Vec3f attenuation) {
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
		this.name = name;

	}

	public String getName() {
		return name;
	}

	// вернуть затухание
	public Vec3f getAttenuation() {
		return attenuation;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public Vec3f getColour() {
		return colour;
	}

	public void setColour(Vec3f colour) {
		this.colour = colour;
	}

}
