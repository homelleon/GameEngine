package renderer.gpgpu;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import object.texture.TBO;
import object.texture.Texture2D;
import primitive.buffer.VBO;
import shader.gpgpu.HeightMapShader;

public class HeightMapRenderer {
	
	private HeightMapShader shader;
	private Texture2D heightMap;
	private VBO vbo;
	private int size;

	
	public HeightMapRenderer(int size, VBO vbo) {
		this.size = size;
		this.vbo = vbo;
		shader = new HeightMapShader();
	}
	
	public void render() {
		// prepare empty texture for height map
		heightMap = Texture2D.create(size, size, 1, false);
		heightMap.bind();
		heightMap.bilinearFilter();
		GL42.glTexStorage2D(GL11.GL_TEXTURE_2D, (int) (Math.log(size) / Math.log(2)), GL30.GL_RGBA32F, size, size);
		
		// create texture buffer object from vertex buffer
		TBO positionMap = TBO.create("positionMap", vbo, GL30.GL_RGB32F);
		
		// render height map texture
		shader.start();
		shader.loadMapSize(size);
		positionMap.bind(1);
		GL42.glBindImageTexture(0, heightMap.getId(), 0, false, 0, GL15.GL_WRITE_ONLY, GL30.GL_RGBA32F);
		int workGroupCount = (int) (size);
		GL43.glDispatchCompute(workGroupCount, workGroupCount, 1);
		GL42.glMemoryBarrier(GL43.GL_SHADER_STORAGE_BARRIER_BIT);
		GL11.glFinish();
		shader.stop();
		
		// setting filter for height map
		heightMap.bind();
		heightMap.bilinearFilter();
		
		// delete texture buffer object
		positionMap.unbind();
		positionMap.delete();
	}
	
	public Texture2D getHeightMap() {
		return heightMap;
	}
	
	public void clean() {
		shader.stop();
		shader.clean();
	}

}
