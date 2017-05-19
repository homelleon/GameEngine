package gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIGroup implements GUIGroupInterface {
	
	String name;
	Map<String, GUIInterface> guis = new HashMap<String, GUIInterface>();
	
	
	public GUIGroup(String name, List<GUIInterface> guiList) {
		for(GUIInterface gui : guiList) {
			this.guis.put(gui.getName(), gui);
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	

	@Override
	public void showAll() {
		guis.values().forEach(gui -> gui.show());
	}

	@Override
	public void hideAll() {
		guis.values().forEach(gui -> gui.hide());		
	}	
	
	@Override
	public GUIInterface get(String name) {		
		return this.guis.get(name);
	}

	@Override
	public Collection<GUIInterface> getAll() {
		return guis.values();
	}

	@Override
	public void cleanAll() {
		for(GUIInterface gui : guis.values()) {
			gui.delete();
		}
		guis.clear();
	}

}
