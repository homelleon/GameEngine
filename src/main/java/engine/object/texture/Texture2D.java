package object.texture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL31;
import org.newdawn.slick.opengl.Texture;

import primitive.buffer.TextureBufferLoader;

public class Texture2D {
	
	private String name;
	private int id;
	private int width;
	private int height;
	private int numberOfRows = 1;
	private boolean hasTransparency = false;
	
	public static Texture2D create(int width, int height, int numberOfRows, boolean hasTransparency) {
		Texture2D texture = new Texture2D();
		texture.generate();
		texture.width = width;
		texture.height = height;
		texture.numberOfRows = numberOfRows;
		texture.hasTransparency = hasTransparency;
		return texture;
	}
	
	private Texture2D(){}
	
	
	public Texture2D(String name, String file) {
		Texture texture = TextureBufferLoader.loadOldTexture(file);
		this.name = name;
		this.height = texture.getTextureHeight();
		this.width = texture.getTextureWidth();
		this.id = texture.getTextureID();
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void bindAsBuffer() {
		glBindTexture(GL31.GL_TEXTURE_BUFFER, id);
	}
	
	public void bindAsBuffer(int location) {
		active(location);
		glBindTexture(GL31.GL_TEXTURE_BUFFER, id);
	}
	
	public void bind(int location) {
		active(location);
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void generate() {
		id = glGenTextures();
	}
	
	public void delete() {
		glDeleteTextures(id);
	}
	
	public static void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public static void unbindAsBuffer() {
		glBindTexture(GL31.GL_TEXTURE_BUFFER, 0);
	}
	
	public static void noFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	}
	
	public static void bilinearFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	}
	
	public static void repeatWrap() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	}
	
	public int getId() {
		return id;
	}

	public String getName()	{
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

	public void setHasTransparency(boolean hasTransparency)	{
		this.hasTransparency = hasTransparency;
	}
	
	public static void active(int location) {
		if(location >= 0 && location < 31) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + location);
		} else {
			throw new IndexOutOfBoundsException("Incorrect location at texture activation!");
		}
			
	}
}
