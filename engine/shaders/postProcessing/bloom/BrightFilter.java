package shaders.postProcessing.bloom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import shaders.postProcessing.ImageRenderer;

/**
 * Postprocessing filter that change brightness of the scene. 
 * 
 * @author homelleon
 * @version 1.0
 */
public class BrightFilter { 

	private ImageRenderer renderer;
	private BrightFilterShader shader;
	
	public BrightFilter(int width, int height) {
		shader = new BrightFilterShader();
		renderer = new ImageRenderer(width, height);
	}
	
	public void render(int texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}
	
	public int getOutputTexture() {
		return renderer.getOutputTexture();
	}

	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}
	
}
