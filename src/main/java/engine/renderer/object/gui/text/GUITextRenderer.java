package renderer.object.gui.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import manager.gui.font.IFontManager;
import object.gui.font.FontType;
import object.gui.text.GUIText;
import object.openglObject.VAO;
import shader.font.FontShader;
import tool.openGL.OGLUtils;

public class GUITextRenderer {

	private IFontManager fontManager;
	private FontShader shader;
	private Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();

	public GUITextRenderer(IFontManager fontManager) {
		this.fontManager = fontManager;
		this.shader = new FontShader();
	}

	public void clean() {
		this.shader.clean();
	}

	public void render(Collection<GUIText> textList) {
		processText(textList);
		prepare();
		this.texts.keySet().forEach(font -> {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			this.texts.get(font).forEach(text -> renderText(text));
		});
		endRendering();
	}

	private void prepare() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		OGLUtils.depthTest(false);
		this.shader.start();
	}

	private void processText(Collection<GUIText> textList) {
		for (GUIText text : textList) {
			if (text.getIsVisible()) {
				this.loadText(text);
			}
		}
	}

	private void loadText(GUIText text) {
		String fontName = text.getFont();
		FontType font = this.fontManager.get(fontName);
		List<GUIText> textBatch = this.texts.get(font);
		if (textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			this.texts.put(font, textBatch);
		}
		textBatch.add(text);
	}

	private void renderText(GUIText text) {
		VAO textMeshVao = text.getMesh();
		textMeshVao.bind(0,1);
		this.shader.loadColour(text.getColor());
		this.shader.loadTranslation(text.getPosition());
		this.shader.loadWidthAndEdge(text.getWidth(), text.getEdge());
		this.shader.loadBorderWidthAndEdge(text.getBorderWidth(), text.getBorderEdge());
		this.shader.loadOffset(text.getOffset());
		this.shader.loadOutLineColour(text.getOutlineColor());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		VAO.unbind(0,1);
	}

	private void endRendering() {
		this.shader.stop();
		GL11.glDisable(GL11.GL_BLEND);
		OGLUtils.depthTest(true);
		this.texts.clear();
	}

}
