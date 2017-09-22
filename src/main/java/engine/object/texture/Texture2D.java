package object.texture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;

import org.lwjgl.opengl.GL13;

import primitive.buffer.ImageLoader;

public class Texture2D {
	
	private String name;
	private int id;
	private int width;
	private int height;
	private int numberOfRows = 0;
	private boolean hasTransparency = false;
	
	public Texture2D(){}
	
	public Texture2D(String name, String file)
	{
		id = ImageLoader.loadImage(file);
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void bind(int location)
	{
		activeTexture(location);
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void generate()
	{
		id = glGenTextures();
	}
	
	public void delete()
	{
		glDeleteTextures(id);
	}
	
	public void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}
	
	private void activeTexture(int location) {
		switch(location) {
			case 0:
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				break;
			case 1:
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				break;
			case 2:
				GL13.glActiveTexture(GL13.GL_TEXTURE2);
				break;
			case 3:
				GL13.glActiveTexture(GL13.GL_TEXTURE3);
				break;
			case 4:
				GL13.glActiveTexture(GL13.GL_TEXTURE4);
				break;
			case 5:
				GL13.glActiveTexture(GL13.GL_TEXTURE5);
				break;
			case 6:
				GL13.glActiveTexture(GL13.GL_TEXTURE6);
				break;
			case 7:
				GL13.glActiveTexture(GL13.GL_TEXTURE7);
				break;
			case 8:
				GL13.glActiveTexture(GL13.GL_TEXTURE8);
				break;
			case 9:
				GL13.glActiveTexture(GL13.GL_TEXTURE9);
				break;
			case 10:
				GL13.glActiveTexture(GL13.GL_TEXTURE10);
				break;
			case 11:
				GL13.glActiveTexture(GL13.GL_TEXTURE11);
				break;
			default:
				throw new NullPointerException("Try to active incorrect texture location!");
		}
	}
}
