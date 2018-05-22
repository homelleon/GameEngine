package shader.postprocess.bloom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import primitive.texture.Texture2D;
import shader.postprocess.ImageRenderer;

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

	public void render(Texture2D texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		renderer.renderQuad();
		shader.stop();
	}

	public Texture2D getOutputTexture() {
		return renderer.getOutputTexture();
	}

	public void clean() {
		renderer.cleanUp();
		shader.clean();
	}

}
