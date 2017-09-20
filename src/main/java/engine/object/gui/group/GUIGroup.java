package object.gui.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import object.gui.gui.IGUI;
import object.gui.pattern.object.GUIObject;
import tool.math.vector.Vec2f;

public class GUIGroup extends GUIObject implements IGUIGroup {

	private int priorityNumber = 0;
	private Map<String, IGUI> guis = new HashMap<String, IGUI>();

	public GUIGroup(String name, List<IGUI> guiList) {
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

	@Override
	public void add(IGUI gui) {
		this.guis.put(gui.getName(), gui);
	}

	@Override
	public void addAll(List<IGUI> guiList) {
		guiList.forEach(gui -> this.guis.put(gui.getName(), gui));
	}

	@Override
	public IGUI get(String name) {
		IGUI gui = null;
		if (!guis.isEmpty()) {
			gui = this.guis.get(name);
		}
		return gui;
	}

	@Override
	public Collection<IGUI> getAll() {
		return guis.values();
	}

	@Override
	public void move(Vec2f position) {
		this.guis.values().forEach(gui -> gui.move(position));		
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
			for (IGUI gui : guis.values()) {
				gui.clean();
			}
		}
		guis.clear();
	}

}
