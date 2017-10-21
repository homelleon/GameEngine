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
	private int n;
	private float strength;
	
	public NormalMapRenderer(int n) {
		this.n = n;
		normalMap = Texture2D.create(n, n, 1, false);
		GL42.glTexStorage2D(GL11.GL_TEXTURE_2D,	(int) (Math.log(n) / Math.log(2)), GL30.GL_RGBA32F, n, n);
		shader = new NormalMapShader();
	}
	
	public void render(Texture2D heightMap) {
		
		shader.start();
		heightMap.bind(0);
		shader.updateUniforms(n, strength);
		GL42.glBindImageTexture(0, normalMap.getId(), 0, false, 0, GL15.GL_WRITE_ONLY, GL30.GL_RGBA32F);
		GL43.glDispatchCompute(n / 16, n / 16, 1);
		GL11.glFinish();
		heightMap.bilinearFilter();
		shader.stop();
		
	}
	
	public Texture2D getNormalMap() {
		return this.normalMap;
	}
	
	public void setStrength(float strength) {
		this.strength = strength;
	}
	
	public void clean() {
		shader.stop();
		shader.clean();
	}

}
