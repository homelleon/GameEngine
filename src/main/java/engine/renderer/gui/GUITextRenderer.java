package renderer.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import manager.gui.FontManager;
import object.gui.font.FontType;
import object.gui.text.GUIText;
import primitive.buffer.VAO;
import shader.font.FontShader;
import tool.GraphicUtils;

public class GUITextRenderer {

	private FontManager fontManager;
	private FontShader shader;
	private Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();

	public GUITextRenderer(FontManager fontManager) {
		this.fontManager = fontManager;
		this.shader = new FontShader();
	}

	public void clean() {
		shader.clean();
	}

	public void render(Collection<GUIText> textList) {
		processText(textList);
		prepare();
		texts.keySet().forEach(font -> {
			font.getTextureAtlas().bind(0);
			texts.get(font).forEach(text -> renderText(text));
		});
		endRendering();
	}

	private void prepare() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GraphicUtils.depthTest(false);
		shader.start();
	}

	private void processText(Collection<GUIText> textList) {
		for (GUIText text : textList) {
			if (!text.getIsVisible()) continue;
			loadText(text);
		}
	}

	private void loadText(GUIText text) {
		String fontName = text.getFont();
		FontType font = this.fontManager.get(fontName);
		List<GUIText> textBatch = texts.get(font);
		if (textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}

	private void renderText(GUIText text) {
		VAO textMeshVao = text.getMesh();
		textMeshVao.bind(0,1);
		shader.loadColor(text.getColor());
		shader.loadTranslation(text.getPosition());
		shader.loadWidthAndEdge(text.getWidth(), text.getEdge());
		shader.loadBorderWidthAndEdge(text.getBorderWidth(), text.getBorderEdge());
		shader.loadOffset(text.getOffset());
		shader.loadOutLineColor(text.getOutlineColor());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		VAO.unbind(0,1);
	}

	private void endRendering() {
		shader.stop();
		GL11.glDisable(GL11.GL_BLEND);
		GraphicUtils.depthTest(true);
		texts.clear();
	}

}
