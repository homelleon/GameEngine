package userInterfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UIManagerStructured implements UIManager {
	
	private Map<String, UI> uis = new HashMap<String, UI>();

	@Override
	public void add(UI ui) {
		uis.put(ui.getName(), ui);
	}

	@Override
	public void addAll(Collection<UI> uiList) {
		if(!uiList.isEmpty()) {
			for(UI ui : uiList) {
				uis.put(ui.getName(), ui);
			}
		}
	}

	@Override
	public UI getByName(String name) {
		return this.uis.get(name);
	}

	@Override
	public Collection<UI> getAll() {
		return uis.values();
	}

	@Override
	public void cleanUp() {
		uis.clear();
	}

}
