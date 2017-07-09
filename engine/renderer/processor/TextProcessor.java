package renderer.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.font.FontType;
import object.gui.font.TextMeshData;
import object.gui.text.GUIText;
import renderer.Loader;
import renderer.objectRenderer.GUITextRenderer;

public class TextProcessor implements TextProcessorInterface {
	
	private Loader loader;
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private GUITextRenderer renderer;
	
	public TextProcessor(Loader loader) {
		this.loader = loader;
	}
	
	public void render() {
		renderer.render(); 
	}
	
	public void loadText(GUIText text) {
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if(textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public void removeText(GUIText text) {
		List<GUIText> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty()) {
			texts.remove(text.getFont());
		}
		
	}
	
	public void cleanUp() {
		renderer.cleanUp();
	}
	

}
