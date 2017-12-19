package shader;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;

public class UniformBlock {
	
	public static final int FLOAT = 0;
	public static final int INTEGER = 1;
	public static final int BOOLEAN = 2;
	public static final int VECTOR2 = 3;
	public static final int VECTOR3 = 4;
	public static final int VECTOR4 = 5;
	public static final int MATRIX = 6;
	
	private int location;
	private int size;
	Buffer buffer;
	
	private Map<String, Integer> uniforms = new HashMap<String, Integer>();
	
	protected UniformBlock(int location, int size, int type) {
		this.location = location;
		this.size = size;
		switch(type) {
			case FLOAT: this.buffer = BufferUtils.createFloatBuffer(size); break;
			case INTEGER: this.buffer = BufferUtils.createIntBuffer(size); break;
			case BOOLEAN: this.buffer = BufferUtils.createIntBuffer(size); break;
			case VECTOR2: this.buffer = BufferUtils.createFloatBuffer(size * 2); break;
			case VECTOR3: this.buffer = BufferUtils.createFloatBuffer(size * 3); break;
			case VECTOR4: this.buffer = BufferUtils.createFloatBuffer(size * 4); break;
			case MATRIX: this.buffer = BufferUtils.createFloatBuffer(size * 16); break;
			default: throw new NullPointerException("Wrong uniform block type!");
		}
	}
	
	protected void addUniform(String name, int offset) {
		uniforms.put(name, offset);
	}
	
	protected int getUniformNumber() {
		return uniforms.size();
	}
	
	protected int getUniformOffset(String name) {
		return uniforms.get(name);
	}

	protected int getLocation() {
		return location;
	}
	
	protected int getSize() {
		return size;
	}
	
	public void loadFloat(String uniformName, float value) {
		
	}
	
}