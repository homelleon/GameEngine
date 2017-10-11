package primitive.buffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL40;

import tool.math.vector.Vector2f;

public class patchVAO {
	
	private VAO vao;
	private VBO vbo;
	private int size;

	public patchVAO() {
		vao = VAO.create();
		vbo = VBO.create(GL15.GL_ARRAY_BUFFER);
	}
	
	public void allocate(Vector2f[] vertices, int patchsize) {
		size = vertices.length;
		vao.bind();
		vbo.bind();
		float[] data = new float[vertices.length * 2];
		for(int i = 0; i < vertices.length; i++) {
			data[2 * i] = vertices[i].getX();
			data[2 * i + 1] = vertices[i].getX();
		}
		vbo.storeData(data);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, Float.BYTES * 2, 0);
		GL40.glPatchParameteri(GL40.GL_PATCH_VERTICES, patchsize);
		VAO.unbind();
	}
}
