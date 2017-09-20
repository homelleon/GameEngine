package object.gui.texture;

import org.newdawn.slick.opengl.Texture;

import tool.math.vector.Vec2f;
import tool.math.vector.Vec3f;

public class GUITexture {

	private String name;
	private boolean isVisible = false;

	private Texture texture;
	private Vec2f position;
	private Vec2f scale;
	private Vec3f mixColor = new Vec3f(1,0,0);
	private boolean isMixColored = false;
	private float transparency = 0;

	public GUITexture(String name, Texture texture, Vec2f position, Vec2f scale) {
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

	public synchronized void setPosition(Vec2f position) {
		this.position = position;
	}

	public Vec2f getPosition() {
		return this.position;
	}

	public Vec2f getScale() {
		return this.scale;
	}
	
	public synchronized void setMixColor(Vec3f color) {
		this.mixColor = color;
	}
	
	public Vec3f getMixColor() {
		return this.mixColor;
	}

	public boolean isMixColored() {
		return this.isMixColored;
	}

	public synchronized void setMixColored(boolean isMixColored) {
		this.isMixColored = isMixColored;
	}
	
	public synchronized void increaseScale(Vec2f scale) {
		this.scale = new Vec2f(this.scale.x + scale.x, this.scale.y + scale.y);
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
