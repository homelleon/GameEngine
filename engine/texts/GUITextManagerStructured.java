package texts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import renderEngine.Loader;

public class GUITextManagerStructured implements GUITextManager {
	
	private TextMaster master; 
	private Map<String, GUIText> texts = new HashMap<String, GUIText>();
	
	public GUITextManagerStructured(Loader loader) {
		this.master = new TextMaster(loader);
	}
	
	@Override
	public void addAll(Collection<GUIText> textList) {
		if((textList != null) && (!textList.isEmpty())) {
			for(GUIText text : textList) {
				this.texts.put(text.getName(), text);
				this.master.loadText(text);
			}
		}	
	}

	@Override
	public void add(GUIText text) {
		if(text != null) {
			this.texts.put(text.getName(), text); 
			this.master.loadText(text);
		}
	}

	@Override
	public GUIText getByName(String name) {
		GUIText text = null;
		if(this.texts.containsKey(name)) {
			text = this.texts.get(name);
		}
		return text;
	}

	@Override
	public Collection<GUIText> getAll() {
		return this.texts.values();
	}
	
	

	@Override
	public void clearAll() {
		this.master.cleanUp();
		this.texts.clear();
	}

	@Override
	public TextMaster getMaster() {
		return master;
	}

	@Override
	public void readFile(String fileName) {
		GUITextLoader txtLoader = new GUITextTXTLoader();
		this.addAll(txtLoader.loadFile(fileName, master));
		
	}

}
