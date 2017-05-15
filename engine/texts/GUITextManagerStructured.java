package texts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fontMeshCreator.GUIText;
import fontRendering.TextMaster;

public class GUITextManagerStructured implements GUITextManager {
	
	//TODO: get textMaster object from scene
	private TextMaster master = new TextMaster();
	private Map<String, GUIText> texts = new HashMap<String, GUIText>();
	
	@Override
	public void addAll(Collection<GUIText> textList) {
		if((textList != null) && (!textList.isEmpty())) {
			for(GUIText text : textList) {
				this.texts.put(text.getName(), text);
			}
		}	
	}

	@Override
	public void add(GUIText text) {
		if(text != null) {
			this.texts.put(text.getName(), text); 		
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
		txtLoader.loadFile(fileName, master);
	}

}
