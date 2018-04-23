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
		if (guiList.isEmpty()) return;
		guiList.forEach(gui -> guis.put(gui.getName(), gui));
	}

	public GUIGroup(String name) {
		super(name);
	}

	@Override
	public void show() {
		super.show();
		if (guis.isEmpty()) return;
		guis.values().forEach(GUI::show);
	}

	@Override
	public void hide() {
		super.hide();
		if (guis.isEmpty()) return;
		guis.values().forEach(GUI::hide);
	}

	public void add(GUI gui) {
		guis.put(gui.getName(), gui);
	}

	public void addAll(List<GUI> guiList) {
		guiList.forEach(gui -> guis.put(gui.getName(), gui));
	}

	public GUI get(String name) {
		if (guis.isEmpty()) return null;
		return guis.get(name);
	}

	public Collection<GUI> getAll() {
		return guis.values();
	}

	public void move(Vector2f position) {
		guis.values().forEach(gui -> gui.move(position));		
	}

	public int getPriorityNumber() {
		return priorityNumber;
	}

	public void setPriorityNumber(int number) {
		priorityNumber = number;
	}

	public void clean() {
		if (guis.isEmpty()) return;
		guis.values().forEach(GUI::clean);
		guis.clear();
	}

}
