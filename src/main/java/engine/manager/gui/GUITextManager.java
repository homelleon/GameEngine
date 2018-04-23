package manager.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.EngineDebug;
import object.gui.font.TextMeshData;
import object.gui.text.GUIText;
import object.gui.text.TextRebuildManager;
import primitive.buffer.Loader;
import primitive.buffer.VAO;

/**
 * Manages GUI text rendered in display.
 * 
 * @author homelleon
 * @see IGUITextManager
 * @see GUIText
 */
public class GUITextManager {

	private FontManager fontManager;
	private Map<String, GUIText> texts = new HashMap<String, GUIText>();

	/**
	 * Constracts GUITextManager manager.
	 */
	public GUITextManager() {
		this.fontManager = new FontManager();
	}

	public void addAll(Collection<GUIText> textList) {
		if (textList == null || textList.isEmpty()) {
			if (EngineDebug.hasDebugPermission())
				System.err.println("Trying to add null collection value into GUITextManager array!");
			return;
		}
		
		textList.forEach(text -> add(text));
	}

	public void add(GUIText text) {
		if (text == null) {
			if (EngineDebug.hasDebugPermission())
				System.err.println("Trying to add null value into GUITextManager array!");
			return;
		}
		
		texts.put(text.getName(), text);
		String font = text.getFont();
		fontManager.create(font);
		TextMeshData data = fontManager.get(font).loadText(text);
		VAO vao = Loader.getInstance().getVertexLoader().loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
	}
	
	public void changeContent(String name, String content) {
		if (!texts.containsKey(name)) {
			if (EngineDebug.hasDebugPermission())
				System.err.println("Can't change text because can't find text with name: " + name + "!");
			return;
		}
		
		GUIText text = texts.get(name);
		text.delete();
		TextRebuildManager.changeContent(text, content);
		String font = text.getFont();
		TextMeshData data = fontManager.get(font).loadText(text);
		VAO vao = Loader.getInstance().getVertexLoader().loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
	}

	public GUIText get(String name) {
		return texts.containsKey(name) ? texts.get(name) : null;
	}

	public Collection<GUIText> getAll() {
		return texts.values();
	}

	public boolean delete(String name) {
		if (!texts.containsKey(name)) return false;		
		texts.get(name).delete();
		texts.remove(name);
		return true;
	}

	public void clean() {
		fontManager.clean();
		texts.values().forEach(GUIText::delete);
		texts.clear();
	}

	public FontManager getFonts() {
		return fontManager;
	}

}
