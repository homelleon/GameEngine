package manager.gui.text;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import manager.gui.font.FontManager;
import manager.gui.font.IFontManager;
import object.gui.font.TextMeshData;
import object.gui.text.GUIText;
import renderer.loader.Loader;

/**
 * Manager for text that rendered in the screen.
 * 
 * @author homelleon
 * @see IGUITextManager
 */
public class GUITextManager implements IGUITextManager {

	private IFontManager fontManager;
	private Map<String, GUIText> texts = new HashMap<String, GUIText>();

	/**
	 * Constracts GUITextManager manager.
	 * 
	 * @param loader
	 *            {@link Loader}
	 */
	public GUITextManager() {
		this.fontManager = new FontManager();
	}

	@Override
	public void addAll(Collection<GUIText> textList) {
		this.addAll((Collection<GUIText>)textList);
	}

	@Override
	public void addAll(List<GUIText> textList) {
		if ((textList != null) && (!textList.isEmpty())) {
			textList.forEach(text -> this.add(text));
		}
	}

	@Override
	public void add(GUIText text) {
		if (text != null) {
			this.texts.put(text.getName(), text);
			String font = text.getFont();
			this.fontManager.create(font);
			TextMeshData data = fontManager.get(font).loadText(text);
			int vao = Loader.getInstance().getVertexLoader().loadToVAO(data.getVertexPositions(), data.getTextureCoords());
			text.setMeshInfo(vao, data.getVertexCount());
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
			this.texts.remove(name);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void clean() {
		this.fontManager.clean();
		this.texts.clear();
	}

	@Override
	public IFontManager getFonts() {
		return fontManager;
	}

}
