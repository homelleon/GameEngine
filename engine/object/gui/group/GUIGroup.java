package object.gui.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.gui.GUIInterface;

public class GUIGroup implements GUIGroupInterface {
	
	String name;
	Map<String, GUIInterface> guis = new HashMap<String, GUIInterface>();
	
	
	public GUIGroup(String name, List<GUIInterface> guiList) {
		this.name = name;
		if(!guiList.isEmpty()) {
			guiList.forEach(gui -> this.guis.put(gui.getName(), gui));
		}
	}
	
	public GUIGroup(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	

	@Override
	public void showAll() {
		if(!guis.isEmpty()) {
			this.guis.values().forEach(gui -> gui.show());
		}
	}

	@Override
	public void hideAll() {
		if(!guis.isEmpty()) {
			this.guis.values().forEach(gui -> gui.hide());
		}
	}

	@Override
	public void add(GUIInterface gui) {
		this.guis.put(gui.getName(), gui);		
	}
	

	@Override
	public void addAll(List<GUIInterface> guiList) {
		guiList.forEach(gui -> this.guis.put(gui.getName(), gui));
	}
	
	@Override
	public GUIInterface get(String name) {
		GUIInterface gui = null;
		if(!guis.isEmpty()) {
			gui = this.guis.get(name);
		}
		return gui;
	}

	@Override
	public Collection<GUIInterface> getAll() {
		return guis.values();
	}

	@Override
	public void cleanAll() {
		if(!guis.isEmpty()) {
			for(GUIInterface gui : guis.values()) {
				gui.delete();
			}
		}
		guis.clear();
	}


}
