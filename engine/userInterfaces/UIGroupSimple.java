package userInterfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIGroupSimple implements UIGroup {
	
	String name;
	Map<String, UI> uis = new HashMap<String, UI>();
	
	
	public UIGroupSimple(String name, List<UI> uiList) {
		for(UI ui : uiList) {
			this.uis.put(ui.getName(), ui);
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	

	@Override
	public void showAll() {
		uis.values().forEach(ui -> ui.show());
	}

	@Override
	public void hideAll() {
		uis.values().forEach(ui -> ui.hide());		
	}	
	
	@Override
	public UI get(String name) {		
		return this.uis.get(name);
	}

	@Override
	public Collection<UI> getAll() {
		return uis.values();
	}

	@Override
	public void cleanAll() {
		for(UI ui : uis.values()) {
			ui.delete();
		}
		uis.clear();
	}

}
