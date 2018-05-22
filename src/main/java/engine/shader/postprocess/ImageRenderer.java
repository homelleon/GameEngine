package shader.postprocess;

import org.lwjgl.opengl.GL11;

import primitive.texture.Texture2D;

public class ImageRenderer {

	private Fbo fbo;

	public ImageRenderer(int width, int height) {
		this.fbo = new Fbo().setSize(width, height);
	}

	public ImageRenderer() {}

	public void renderQuad() {
		if (fbo != null)
			fbo.bind();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		if (fbo != null)
			fbo.unbind();
	}

	public Texture2D getOutputTexture() {
		return fbo.getColorTexture();
	}

	public void cleanUp() {
		if (fbo != null)
			fbo.clean();
	}

}
