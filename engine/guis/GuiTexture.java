package guis;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {
	
	private String name;
	private boolean isShown = false;
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	
	public GuiTexture(String name, int texture, Vector2f position, Vector2f scale) {
		this.name = name;
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}
	
	
	public String getName() {
		return name;
	}

	public int getTexture() {
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
	
	public boolean getIsShown() {
		return this.isShown;
	}
	
	public void setIsShown(boolean value) {
		this.isShown = value;
	}
	
	
}
