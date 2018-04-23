package primitive.texture;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL31;

import primitive.buffer.VBO;

/**
 * Texture buffer object.<br>
 * Can be used to store vertex buffer object data as buffer texture.
 *  
 * @author homelleon
 * @version 1.0
 */
public class TBO {
	
	private String name;
	private int id;
	private int size;
	
	/**
	 * Creates texture buffer object as texture from vertex buffer object.
	 * 
	 * @param name - String name
	 * @param vbo - GL_TEXTURE_BUFFER-type vertex buffer object
	 * @param storageType - int type of data storage
	 * @return {@link TBO} texture buffer object
	 */
	public static TBO create(String name, VBO vbo, int storageType) {
		TBO textureBuffer = new TBO();
		textureBuffer.name = name;
		textureBuffer.size = vbo.getSize();
		GL15.glBindBuffer(GL_TEXTURE_BUFFER, vbo.getId());
		textureBuffer.id = GL11.glGenTextures();
		GL11.glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		GL31.glTexBuffer(GL_TEXTURE_BUFFER, storageType, vbo.getId());
		GL15.glBindBuffer(GL_TEXTURE_BUFFER, 0);
		textureBuffer.unbind();
		return textureBuffer;
	}
	
	private TBO() {}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_BUFFER, id);
	}
	
	public void bind(int location) {
		active(location);
		glBindTexture(GL_TEXTURE_BUFFER, id);
	}
	
	public static void active(int location) {
		if (location < 0 || location >= 31)
			throw new IndexOutOfBoundsException("Incorrect location at texture activation!");
		
		GL13.glActiveTexture(GL_TEXTURE0 + location);
	}
	
	public void delete() {
		glDeleteTextures(id);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_BUFFER, 0);
	}
	
	public int getId() {
		return id;
	}

	public String getName()	{
		return name;
	}

	public int getSize() {
		return size;
	}
}
