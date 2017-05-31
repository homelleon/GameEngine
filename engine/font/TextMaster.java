package font;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.settings.ES;
import renderers.Loader;
import renderers.renderers.FontRenderer;

public class TextMaster {
	
	private Loader loader;
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private FontRenderer renderer;
	private FontType font;
	
	public TextMaster(Loader loader) {
		this.loader = loader;
		renderer = new FontRenderer();
		this.font = 
				new FontType(loader.loadTexture(ES.FONT_FILE_PATH, "candara"),
						new File(ES.FONT_FILE_PATH + "candara.fnt"));
	}
	
	public void render() {
		renderer.render(texts); 
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
	
	public static void removeText(GUIText text) {
		List<GUIText> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if(textBatch.isEmpty()) {
			texts.remove(text.getFont());
		}
		
	}
	
	public FontType getFont() {
		return this.font;
	}
	
	public void cleanUp() {
		renderer.cleanUp();
	}
	

}
