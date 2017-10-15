package primitive.buffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL40;

public class PatchVAO {
	
	private VAO vao;
	private VBO vbo;
	private int size;

	public PatchVAO() {
		vao = VAO.create();
		vbo = VBO.create(GL15.GL_ARRAY_BUFFER);
	}
	
	public void bind() {
		vao.bind(0);
	}
	
	public static void unbind() {
		VAO.unbind(0);
	}
	
	public void render() {
		GL11.glDrawArrays(GL40.GL_PATCHES, 0, this.size);
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void allocate(float[] positions, int patchSize) {
		size = positions.length;
		vao.bind();
		vbo.bind();
		vbo.storeData(positions);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, Float.BYTES * 2, 0);
		GL40.glPatchParameteri(GL40.GL_PATCH_VERTICES, patchSize);
		VAO.unbind();
	}
	
	public void delete() {
		vao.bind();
		vbo.delete();
		vao.delete();
		VAO.unbind();
	}
}
