package object.gui.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.gui.GUIInterface;
import object.gui.pattern.object.GUIObject;

public class GUIGroup extends GUIObject implements GUIGroupInterface {

	private int priorityNumber = 0;
	private Map<String, GUIInterface> guis = new HashMap<String, GUIInterface>();

	public GUIGroup(String name, List<GUIInterface> guiList) {
		this.name = name;
		if (!guiList.isEmpty()) {
			guiList.forEach(gui -> this.guis.put(gui.getName(), gui));
		}
	}

	public GUIGroup(String name) {
		this.name = name;
	}

	@Override
	public void show() {
		super.show();
		if (!guis.isEmpty()) {
			this.guis.values().forEach(gui -> gui.show());
		}
	}

	@Override
	public void hide() {
		super.hide();
		if (!guis.isEmpty()) {
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
		if (!guis.isEmpty()) {
			gui = this.guis.get(name);
		}
		return gui;
	}

	@Override
	public Collection<GUIInterface> getAll() {
		return guis.values();
	}

	@Override
	public int getPriorityNumber() {
		return this.priorityNumber;
	}

	@Override
	public void setPriorityNumber(int number) {
		this.priorityNumber = number;
	}

	@Override
	public void clean() {
		if (!guis.isEmpty()) {
			for (GUIInterface gui : guis.values()) {
				gui.clean();
			}
		}
		guis.clear();
	}

}
