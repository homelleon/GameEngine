package renderer.object.gui.texture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import object.gui.texture.GUITexture;
import primitive.buffer.Loader;
import primitive.buffer.VAO;
import primitive.model.Mesh;
import shader.guiTexture.GUITextureShader;
import tool.math.Maths;
import tool.math.Matrix4f;
import tool.math.vector.Vector2f;
import tool.openGL.OGLUtils;

public class GUITextureRenderer {

	private final Mesh quad;
	private GUITextureShader shader;

	public GUITextureRenderer() {
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		this.quad = Loader.getInstance().getVertexLoader().loadToVAO(positions, 2);
		this.shader = new GUITextureShader();
	}

	public void render(Collection<GUITexture> textureList) {
		this.shader.start();
		VAO vao = this.quad.getVAO();
		vao.bind(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OGLUtils.depthTest(false);

		for (GUITexture guiTexture : textureList) {
			if (guiTexture.getIsVisible()) {
				//guiTexture.getTexture().bind(0);
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				glBindTexture(GL_TEXTURE_2D, 27);
				Vector2f scale = new Vector2f(guiTexture.getScale().x, guiTexture.getScale().y);
				Matrix4f matrix = Maths.createTransformationMatrix(guiTexture.getPosition(), scale);
				this.shader.loadTransformation(matrix);
				this.shader.loadMixColorVariables(guiTexture.isMixColored(), guiTexture.getMixColor());
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, this.quad.getVertexCount());
			}
		}

		OGLUtils.depthTest(true);
		GL11.glDisable(GL11.GL_BLEND);
		VAO.unbind(0);
		this.shader.stop();
	}

	public void cleanUp() {
		this.shader.clean();
	}

}
