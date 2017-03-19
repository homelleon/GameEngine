package lights;

import org.lwjgl.util.vector.Vector3f;

/*
 * Light - источник света
 * 03.02.17
 * ------------
 */

public class Light {
	
	private Vector3f position;  //позиция
	private Vector3f colour;  //цвеь
	private Vector3f attenuation = new Vector3f(1, 0, 0); //затухание
	
	private String name;
	
	public Light(String name, Vector3f position, Vector3f colour) {
		this.position = position;
		this.colour = colour;
		this.name = name;
	}
	
	public Light(String name, Vector3f position, Vector3f colour, Vector3f attenuation) {
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
		this.name = name;
		
	}
	
	public String getName() { 
		return name;
	}

	//вернуть затухание
	public Vector3f getAttenuation() {
		return attenuation;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getColour() {
		return colour;
	}
	
	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

}
