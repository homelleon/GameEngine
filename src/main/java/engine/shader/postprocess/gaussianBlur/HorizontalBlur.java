package shader.postprocess.gaussianBlur;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import primitive.texture.Texture2D;
import shader.postprocess.ImageRenderer;

public class HorizontalBlur {

	private ImageRenderer renderer;
	private HorizontalBlurShader shader;

	public HorizontalBlur(int targetFboWidth, int targetFboHeight) {
		shader = new HorizontalBlurShader();
		shader.start();
		shader.loadTargetWidth(targetFboWidth);
		shader.stop();
		renderer = new ImageRenderer(targetFboWidth, targetFboHeight);
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

	public void cleanUp() {
		renderer.cleanUp();
		shader.clean();
	}

}
