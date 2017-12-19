package manager.gui.text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.debug.EngineDebug;
import manager.gui.font.FontManager;
import manager.gui.font.IFontManager;
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
public class GUITextManager implements IGUITextManager {

	private IFontManager fontManager;
	private Map<String, GUIText> texts = new HashMap<String, GUIText>();

	/**
	 * Constracts GUITextManager manager.
	 */
	public GUITextManager() {
		this.fontManager = new FontManager();
	}

	@Override
	public void addAll(Collection<GUIText> textList) {
		if ((textList != null) && (!textList.isEmpty())) {
			textList.forEach(text -> this.add(text));
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null collection value into GUITextManager array!");
			}
		}
	}

	@Override
	public void add(GUIText text) {
		if (text != null) {
			this.texts.put(text.getName(), text);
			String font = text.getFont();
			this.fontManager.create(font);
			TextMeshData data = fontManager.get(font).loadText(text);
			VAO vao = Loader.getInstance().getVertexLoader().loadToVAO(data.getVertexPositions(), data.getTextureCoords());
			text.setMeshInfo(vao, data.getVertexCount());
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null value into GUITextManager array!");
			}
		}
	}
	
	@Override
	public void changeContent(String name, String content) {
		if(this.texts.containsKey(name)) {
			GUIText text = this.texts.get(name);
			text.delete();
			TextRebuildManager.changeContent(text, content);
			String font = text.getFont();
			TextMeshData data = this.fontManager.get(font).loadText(text);
			VAO vao = Loader.getInstance().getVertexLoader().loadToVAO(data.getVertexPositions(), data.getTextureCoords());
			text.setMeshInfo(vao, data.getVertexCount());
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Can't change text because can't find text with name: " + name + "!");
			}
		}
	}

	@Override
	public GUIText get(String name) {
		return this.texts.containsKey(name) ? this.texts.get(name) : null;
	}

	@Override
	public Collection<GUIText> getAll() {
		return this.texts.values();
	}

	@Override
	public boolean delete(String name) {
		if(this.texts.containsKey(name)) {
			this.texts.get(name).delete();
			this.texts.remove(name);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void clean() {
		this.fontManager.clean();
		this.texts.values().forEach(GUIText::delete);
		this.texts.clear();
	}

	@Override
	public IFontManager getFonts() {
		return fontManager;
	}

}
