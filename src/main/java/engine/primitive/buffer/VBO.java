package primitive.buffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class VBO {

	private final int vboId;
	private final int type;
	private int size;

	private VBO(int vboId, int type) {
		this.vboId = vboId;
		this.type = type;
	}

	public static VBO create(int type) {
		int id = GL15.glGenBuffers();
		return new VBO(id, type);
	}

	public void bind() {
		GL15.glBindBuffer(type, vboId);
	}
	
	public void bindBase(int layout) {
		GL30.glBindBufferBase(type, layout, vboId);
	}
	
	public void unbindBase(int layout) {
		GL30.glBindBufferBase(type, layout, 0);
	}

	public void unbind() {
		GL15.glBindBuffer(type, 0);
	}
	
	public void storeSubData(FloatBuffer buffer, float[] data) {
		buffer.clear();
		buffer.put(data);
		buffer.flip();
		GL15.glBufferData(type, buffer.capacity() * 4, GL15.GL_STATIC_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);		
	}

	public void storeData(float[] data) {
		size = data.length;
		FloatBuffer buffer = BufferUtils.createFloatBuffer(size);
		buffer.put(data);
		buffer.flip();
		storeData(buffer);
	}
	
	public void storeData(float[] data, int storageType) {
		size = data.length;
		FloatBuffer buffer = BufferUtils.createFloatBuffer(size);
		buffer.put(data);
		buffer.flip();
		GL15.glBufferData(type, buffer, storageType);
	}

	public void storeData(int[] data) {
		size = data.length;
		IntBuffer buffer = BufferUtils.createIntBuffer(size);
		buffer.put(data);
		buffer.flip();
		storeData(buffer);
	}
	
	public void storeData(int data) {
		GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
	}

	public void storeData(IntBuffer data) {
		GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
	}

	public void storeData(FloatBuffer data) {
		GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
	}
	
	public int getId() {
		return this.vboId;
	}
	
	public int getSize() {
		return size;
	}

	public void delete() {
		GL15.glDeleteBuffers(vboId);
	}

}
