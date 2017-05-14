package texts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fontMeshCreator.GuiText;
import fontRendering.TextMaster;

public class GUITextManagerStructured implements GUITextManager {
	
	private TextMaster master = new TextMaster();
	private Map<String, GuiText> texts = new HashMap<String, GuiText>();
	
	@Override
	public void addAll(Collection<GuiText> textList) {
		if((textList != null) && (!textList.isEmpty())) {
			for(GuiText text : textList) {
				this.texts.put(text.getName(), text);
			}
		}	
	}

	@Override
	public void add(GuiText text) {
		if(text != null) {
			this.texts.put(text.getName(), text); 		
		}
	}

	@Override
	public GuiText getByName(String name) {
		GuiText text = null;
		if(this.texts.containsKey(name)) {
			text = this.texts.get(name);
		}
		return text;
	}

	@Override
	public Collection<GuiText> getAll() {
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
