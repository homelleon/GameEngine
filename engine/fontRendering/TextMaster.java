package fontRendering;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fontMeshCreator.FontType;
import fontMeshCreator.GuiText;
import fontMeshCreator.TextMeshData;
import renderEngine.Loader;
import scene.ES;

public class TextMaster {
	
	private Loader loader;
	private static Map<FontType, List<GuiText>> texts = new HashMap<FontType, List<GuiText>>();
	private FontRenderer renderer;
	private FontType font;
	
	public void init(Loader theLoader) {
		renderer = new FontRenderer();
		loader = theLoader;
		this.font = 
				new FontType(loader.loadTexture(ES.FONT_PATH, "candara"),
						new File(ES.FONT_PATH + "candara.fnt"));
	}
	
	public void render() {
		renderer.render(texts); 
	}
	
	public void loadText(GuiText text) {
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GuiText> textBatch = texts.get(font);
		if(textBatch == null) {
			textBatch = new ArrayList<GuiText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	public static void removeText(GuiText text) {
		List<GuiText> textBatch = texts.get(text.getFont());
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
