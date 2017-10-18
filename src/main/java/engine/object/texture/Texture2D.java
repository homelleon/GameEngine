package object.texture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import org.lwjgl.opengl.GL13;

import primitive.buffer.TextureBufferLoader;

public class Texture2D 
{
	
	private String name;
	private int id;
	private int width;
	private int height;
	private int numberOfRows = 1;
	private boolean hasTransparency = false;
	
	public static Texture2D create(int width, int height, int numberOfRows, boolean hasTransparency) 
	{
		Texture2D texture = new Texture2D();
		texture.id = GL11.glGenTextures();
		texture.width = width;
		texture.height = height;
		texture.numberOfRows = numberOfRows;
		texture.hasTransparency = hasTransparency;
		return texture;
	}
	
	private Texture2D(){}
	
	
	public Texture2D(String name, String file)
	{
		Texture texture = TextureBufferLoader.loadOldTexture(file);
		this.name = name;
		this.height = texture.getTextureHeight();
		this.width = texture.getTextureWidth();
		this.id = texture.getTextureID();
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void bind(int location)
	{
		active(location);
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
	
	public int getId() 
	{
		return id;
	}

	public String getName() 
	{
		return name;
	}

	public int getWidth() 
	{
		return width;
	}

	public int getHeight() 
	{
		return height;
	}

	public int getNumberOfRows() 
	{
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) 
	{
		this.numberOfRows = numberOfRows;
	}

	public boolean isHasTransparency() 
	{
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) 
	{
		this.hasTransparency = hasTransparency;
	}
	
	private void active(int location) 
	{
		if(location >= 0 && location < 31) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + location);
		} else {
			throw new IndexOutOfBoundsException("Incorrect location at texture activation!");
		}
			
	}
}
