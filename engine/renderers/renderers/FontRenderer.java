package renderers.renderers;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import font.FontType;
import font.GUIText;
import shaders.font.FontShader;
import toolbox.OGLUtils;

public class FontRenderer {

	private FontShader shader;

	public FontRenderer() {
		shader = new FontShader(); 
	}

	public void cleanUp() {
		shader.cleanUp();
	}
	
	public void render(Map<FontType, List<GUIText>> texts) {
		prepare();
		
		for(FontType font: texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			
			for(GUIText text : texts.get(font)) {
				if(text.getIsShown()) {
					renderText(text);
				}
			}
		}
		endRendering();
	}
	
	private void prepare() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OGLUtils.depthTest(false);
		shader.start();
	}
	
	private void renderText(GUIText text) {
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadColour(text.getColour());
		shader.loadTranslation(text.getPosition());
		shader.loadWidthAndEdge(text.getWidth(), text.getEdge());
		shader.loadBorderWidthAndEdge(text.getBorderWidth(), text.getBorderEdge());
		shader.loadOffset(text.getOffset());
		shader.loadOutLineColour(text.getOutlineColour());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void endRendering() {
		shader.stop();
		GL11.glDisable(GL11.GL_BLEND);
		OGLUtils.depthTest(true);
	}

}