package postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import scene.ES;

public class ContrastChanger {
	
	private ImageRenderer renderer;
	private ContrastShader shader;
	
	public ContrastChanger(){
		shader = new ContrastShader();
		renderer = new ImageRenderer();
	}
	
	public void render(int texture) {
		shader.start();
		shader.loadDisplayContrast(ES.DISPLAY_CONTRAST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}
	
	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}

}
