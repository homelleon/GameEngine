package object.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.element.GUIObject;
import tool.math.vector.Vector2f;

public class GUIGroup extends GUIObject {

	private int priorityNumber = 0;
	private Map<String, GUI> guis = new HashMap<String, GUI>();

	public GUIGroup(String name, List<GUI> guiList) {
		super(name);
		if (!guiList.isEmpty()) {
			guiList.forEach(gui -> this.guis.put(gui.getName(), gui));
		}
	}

	public GUIGroup(String name) {
		super(name);
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

	public void add(GUI gui) {
		this.guis.put(gui.getName(), gui);
	}

	public void addAll(List<GUI> guiList) {
		guiList.forEach(gui -> this.guis.put(gui.getName(), gui));
	}

	public GUI get(String name) {
		GUI gui = null;
		if (!guis.isEmpty()) {
			gui = this.guis.get(name);
		}
		return gui;
	}

	public Collection<GUI> getAll() {
		return guis.values();
	}

	public void move(Vector2f position) {
		this.guis.values().forEach(gui -> gui.move(position));		
	}

	public int getPriorityNumber() {
		return this.priorityNumber;
	}

	public void setPriorityNumber(int number) {
		this.priorityNumber = number;
	}

	public void clean() {
		if (!guis.isEmpty()) {
			for (GUI gui : guis.values()) {
				gui.clean();
			}
		}
		guis.clear();
	}

}
