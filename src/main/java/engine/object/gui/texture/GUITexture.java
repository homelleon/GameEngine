package object.gui.texture;

import primitive.texture.Texture2D;
import scene.Drawable;
import shader.Shader;
import shader.ShaderPool;
import tool.math.vector.Color;
import tool.math.vector.Vector2f;

public class GUITexture extends Drawable<Vector2f> {

	private boolean isVisible = false;

	private Texture2D texture;
	private Vector2f scale;
	private Color mixColor = new Color(255, 0, 0);
	private boolean isMixColored = false;
	private float transparency = 0;

	public GUITexture(String name, Texture2D texture, Vector2f position, Vector2f scale) {
		super(name, ShaderPool.INSTANCE.get(Shader.GUI_TEXTURE), null);
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public Texture2D getTexture() {
		return texture;
	}
	
	public Vector2f getScale() {
		return scale;
	}
	
	public void setMixColor(Color color) {
		this.mixColor = color;
	}
	
	public Color getMixColor() {
		return mixColor;
	}

	public boolean isMixColored() {
		return isMixColored;
	}

	public void setMixColored(boolean isMixColored) {
		this.isMixColored = isMixColored;
	}
	
	public void increaseScale(Vector2f value) {
		scale = new Vector2f(scale.x + value.x, scale.y + value.y);
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
		return transparency;
	}

	public boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean value) {
		isVisible = value;
	}
	
	public GUITexture clone(String name) {
		return new GUITexture(name, texture, position, scale);
	}

}
