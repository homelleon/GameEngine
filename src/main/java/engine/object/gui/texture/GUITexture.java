package object.gui.texture;

import object.texture.Texture2D;
import tool.math.vector.Vector2f;
import tool.math.vector.Vector3fF;

public class GUITexture {

	private String name;
	private boolean isVisible = false;

	private Texture2D texture;
	private Vector2f position;
	private Vector2f scale;
	private Vector3fF mixColor = new Vector3fF(1,0,0);
	private boolean isMixColored = false;
	private float transparency = 0;

	public GUITexture(String name, Texture2D texture, Vector2f position, Vector2f scale) {
		this.name = name;
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public String getName() {
		return name;
	}

	public Texture2D getTexture() {
		return this.texture;
	}

	public synchronized void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getPosition() {
		return this.position;
	}

	public Vector2f getScale() {
		return this.scale;
	}
	
	public synchronized void setMixColor(Vector3fF color) {
		this.mixColor = color;
	}
	
	public Vector3fF getMixColor() {
		return this.mixColor;
	}

	public boolean isMixColored() {
		return this.isMixColored;
	}

	public synchronized void setMixColored(boolean isMixColored) {
		this.isMixColored = isMixColored;
	}
	
	public synchronized void increaseScale(Vector2f scale) {
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

	public synchronized void setIsVisible(boolean value) {
		this.isVisible = value;
	}
	
	public GUITexture clone(String name) {
		return new GUITexture(name, this.texture, this.position, this.scale);
	}

}
