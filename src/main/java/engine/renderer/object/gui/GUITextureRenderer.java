package renderer.object.gui;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import core.display.DisplayManager;
import object.gui.texture.GUITexture;
import object.model.raw.RawModel;
import renderer.loader.Loader;
import shader.guiTexture.GUITextureShader;
import tool.math.Maths;
import tool.openGL.OGLUtils;

public class GUITextureRenderer {

	private final RawModel quad;
	private GUITextureShader shader;

	public GUITextureRenderer() {
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		this.quad = Loader.getInstance().getVertexLoader().loadToVAO(positions, 2);
		this.shader = new GUITextureShader();
	}

	public void render(Collection<GUITexture> textureList) {
		this.shader.start();
		GL30.glBindVertexArray(this.quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OGLUtils.depthTest(false);

		for (GUITexture guiTexture : textureList) {
			if (guiTexture.getIsShown()) {
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, guiTexture.getTexture().getTextureID());
				Vector2f size = new Vector2f(guiTexture.getTexture().getImageWidth() / (float) DisplayManager.getWidth(), 
						 guiTexture.getTexture().getImageHeight() / (float) DisplayManager.getHeight());
				Vector2f scale = new Vector2f(size.x * guiTexture.getScale().getX(), size.y * guiTexture.getScale().getY());
				Matrix4f matrix = Maths.createTransformationMatrix(guiTexture.getPosition(), scale);
				this.shader.loadTransformation(matrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, this.quad.getVertexCount());
			}
		}

		OGLUtils.depthTest(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		this.shader.stop();
	}

	public void cleanUp() {
		this.shader.clean();
	}

}
