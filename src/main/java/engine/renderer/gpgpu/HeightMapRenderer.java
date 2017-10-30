package renderer.gpgpu;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import object.texture.Texture2D;
import primitive.buffer.VAO;
import shader.gpgpu.HeightMapShader;

public class HeightMapRenderer {
	
	private HeightMapShader shader;
	private VAO vao;
	private Texture2D heightMap;
	private int size;
	
	public HeightMapRenderer(int size, VAO vao) {
		this.size = size;
		this.vao = vao;
		heightMap = Texture2D.create(size, size, 1, false);
		GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, (int) (Math.log(size) / Math.log(2)), GL30.GL_RGBA32F, size, size);
		Texture2D.unbind();
		shader = new HeightMapShader();
	}
	
	public void render() {
		shader.start();
		vao.bind(0);
		GL30.glBindBufferBase(GL31.GL_UNIFORM_BUFFER, index, buffer);
		shader.loadMapSize(size);
		GL42.glBindImageTexture(0, heightMap.getId(), 0, false, 0, GL15.GL_WRITE_ONLY, GL30.GL_RGBA32F);
		GL43.glDispatchCompute(size / 16, size / 16, 1);
		GL11.glFinish();
		Texture2D.bilinearFilter();
		shader.stop();		
	}
	
	public Texture2D getHeightMap() {
		return heightMap;
	}
	
	public void clean() {
		shader.stop();
		shader.clean();
		heightMap.delete();
	}

}
