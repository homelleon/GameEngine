package renderer.object.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import object.gui.font.FontType;
import object.gui.text.GUIText;
import object.gui.text.manager.GUITextManagerInterface;
import shader.font.FontShader;
import tool.openGL.OGLUtils;

public class GUITextRenderer {

	private FontShader shader;
	private GUITextManagerInterface textManager;
	private Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();

	public GUITextRenderer(GUITextManagerInterface textManager) {
		this.textManager = textManager;
		this.shader = new FontShader();
	}

	public void cleanUp() {
		this.shader.cleanUp();
	}
	
	public void render() {
		processText(this.textManager.getAll());
		prepare();
		for(FontType font: this.texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());			
			for(GUIText text : this.texts.get(font)) {
				renderText(text);
			}
		}
		endRendering();
	}
	
	private void prepare() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OGLUtils.depthTest(false);
		this.shader.start();
	}
	
	private void processText(Collection<GUIText> textList) {
		for(GUIText text : textList) {			
			if(text.getIsShown()) {
				this.loadText(text);
			}
		}
	}
	
	private void loadText(GUIText text) {
		String fontName = text.getFont();
		FontType font = this.textManager.getFonts().get(fontName);
		List<GUIText> textBatch = this.texts.get(font);
		if(textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			this.texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	private void renderText(GUIText text) {
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		this.shader.loadColour(text.getColour());
		this.shader.loadTranslation(text.getPosition());
		this.shader.loadWidthAndEdge(text.getWidth(), text.getEdge());
		this.shader.loadBorderWidthAndEdge(text.getBorderWidth(), text.getBorderEdge());
		this.shader.loadOffset(text.getOffset());
		this.shader.loadOutLineColour(text.getOutlineColour());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void endRendering() {
		this.shader.stop();
		GL11.glDisable(GL11.GL_BLEND);
		OGLUtils.depthTest(true);
		this.texts.clear();
	}

}
