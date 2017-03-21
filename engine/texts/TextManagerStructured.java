package texts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fontMeshCreator.GuiText;
import fontRendering.TextMaster;

public class TextManagerStructured implements TextManager {
	
	private TextMaster master = new TextMaster();
	private Map<String, GuiText> texts = new HashMap<String, GuiText>();
	private List<GuiText> aTextList = new ArrayList<GuiText>();
	private Map<Float, List<TimeText>> timeTexts = new HashMap<Float, List<TimeText>>();
	
	public void init() {
		
	}
	
	public void addText(GuiText text) {
		this.aTextList.add(text);
	}
	
	public void addText(GuiText text, float duration, float time) {
		List<TimeText> batch = timeTexts.get(time);
		if(batch != null) {
			batch.add(new TimeText(text, duration, time));
		} else {
			List<TimeText> newBatch = new ArrayList<TimeText>();
			newBatch.add(new TimeText(text, duration, time));
			this.timeTexts.put(time, newBatch);
		}
	}
	
	public void update(int time) {
		//clear timeTexts if time is up
		for(Float key : this.timeTexts.keySet()) {
			if (key < time) {
				List<TimeText> batch = timeTexts.get(key);
				for(int i = 0; i < batch.size(); i++) {
					TimeText text = timeTexts.get(key).get(i);
					if((text.getStartTime() + text.getDuration()) < time) {
						batch.remove(i); //remove text
					}
				}
				if(batch.isEmpty()) {
					timeTexts.remove(key); //remove list of text
				}
			}
		}
		
		//add timeText to renderingText
		
		for(Float key : this.timeTexts.keySet()) {
			if(key >= time) {
				
			}
		}
	
	}
	
	public void render() {
		master.render();
	}

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

}
