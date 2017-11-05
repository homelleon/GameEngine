package renderer.gpgpu;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;

import object.texture.Texture2D;
import shader.gpgpu.NormalMapShader;

public class NormalMapRenderer {
	
	private NormalMapShader shader;
	private Texture2D normalMap;
	private Texture2D heightMap;
	private int size;
	private float strength;
	
	public NormalMapRenderer(int size) {
		this.size = size;
		normalMap = Texture2D.create(size, size, 1, false);
		normalMap.bind();
		normalMap.bilinearFilter();
		GL42.glTexStorage2D(GL11.GL_TEXTURE_2D,	(int) (Math.log(size) / Math.log(2)), GL30.GL_RGBA32F, size, size);
		shader = new NormalMapShader();
		shader.start();
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void render() {		
		shader.start();
		shader.updateUniforms(size, strength);
		GL42.glBindImageTexture(0, normalMap.getId(), 0, false, 0, GL15.GL_WRITE_ONLY, GL30.GL_RGBA32F);
		heightMap.bind(0);
		GL43.glDispatchCompute(size / 16, size / 16, 1);
		GL42.glMemoryBarrier(GL43.GL_SHADER_STORAGE_BARRIER_BIT);
		GL11.glFinish();
		normalMap.bind();
		normalMap.bilinearFilter();
		shader.stop();		
	}
	
	public Texture2D getNormalMap() {
		return normalMap;
	}
	
	public void setHeightMap(Texture2D heightMap) {
		this.heightMap = heightMap;
	}
	
	public void setStrength(float strength) {
		this.strength = strength;
	}
	
	public void clean() {
		shader.stop();
		shader.clean();
	}

}
