package renderers.renderers;

import java.util.Collection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import guis.guiTextures.GUITexture;
import objects.models.RawModel;
import renderers.Loader;
import shaders.guiTextures.GUITextureShader;
import toolbox.Maths;
import toolbox.OGLUtils;

public class GUITextureRenderer {
	
	private final RawModel quad;
	private GUITextureShader shader;
	
	public GUITextureRenderer(Loader loader) {
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(positions, 2);
		shader = new GUITextureShader();
	}
	
	public void render(Collection <GUITexture> guiTextureList) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OGLUtils.depthTest(false);
		
		for(GUITexture guiTexture: guiTextureList){
			if(guiTexture.getIsShown()) {
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, guiTexture.getTexture());
				Matrix4f matrix = Maths.createTransformationMatrix(guiTexture.getPosition(), guiTexture.getScale());
				shader.loadTransformation(matrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			}
		}
		
		OGLUtils.depthTest(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}

}