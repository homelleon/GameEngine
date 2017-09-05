package object.gui.texture;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

public class GUITexture {

	private String name;
	private boolean isVisible = false;

	private Texture texture;
	private Vector2f position;
	private Vector2f scale;
	private float transparency = 0;

	public GUITexture(String name, Texture texture, Vector2f position, Vector2f scale) {
		this.name = name;
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public String getName() {
		return name;
	}

	public Texture getTexture() {
		return this.texture;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getPosition() {
		return this.position;
	}

	public Vector2f getScale() {
		return this.scale;
	}
	
	public void increaseScale(Vector2f scale) {
		this.scale = new Vector2f(this.scale.x + scale.x, this.scale.y + scale.y);
	}
	
	/**
	 * Sets trasparency value.
	 * 
	 * @param value float of texture transparency
	 */
	public void setTransparency(float value) {
		this.transparency = value;
	}
	
	/**
	 * Gets if the texture is should be visible.
	 * 
	 * @return true if text is visible<br>
	 * 		   false if text is invisible
	 */
	public float getTransparency() {
		return this.transparency;
	}

	public boolean getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(boolean value) {
		this.isVisible = value;
	}
	
	public GUITexture clone(String name) {
		return new GUITexture(name, this.texture, this.position, this.scale);
	}

}
