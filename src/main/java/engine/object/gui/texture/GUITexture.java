package object.gui.texture;

import object.GameObject;
import primitive.texture.Texture2D;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;

public class GUITexture extends GameObject {

	private boolean isVisible = false;

	private Texture2D texture;
	private Vector2f position;
	private Vector2f scale;
	private Color mixColor = new Color(255, 0, 0);
	private boolean isMixColored = false;
	private float transparency = 0;

	public GUITexture(String name, Texture2D texture, Vector2f position, Vector2f scale) {
		super(name);
		this.texture = texture;
		this.position = position;
		this.scale = scale;
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
	
	public void setMixColor(Color color) {
		this.mixColor = color;
	}
	
	public Color getMixColor() {
		return this.mixColor;
	}

	public boolean isMixColored() {
		return this.isMixColored;
	}

	public void setMixColored(boolean isMixColored) {
		this.isMixColored = isMixColored;
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
