package guis;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {
	
	private String name;
	
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
		return texture;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public Vector2f getScale() {
		return scale;
	}
	
	
}