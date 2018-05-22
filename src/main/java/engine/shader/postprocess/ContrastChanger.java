package shader.postprocess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import core.settings.EngineSettings;
import primitive.texture.Texture2D;

public class ContrastChanger {

	private ImageRenderer renderer;
	private ContrastShader shader;

	public ContrastChanger() {
		shader = new ContrastShader();
		renderer = new ImageRenderer();
	}

	public void render(Texture2D texture) {
		shader.start();
		shader.loadDisplayContrast(EngineSettings.DISPLAY_CONTRAST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		renderer.renderQuad();
		shader.stop();
	}

	public void clean() {
		renderer.cleanUp();
		shader.clean();
	}

}
