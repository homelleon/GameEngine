package object.gui.group.builder;

import java.util.ArrayList;
import java.util.List;

import object.gui.group.GUIGroup;
import object.gui.group.IGUIGroup;
import object.gui.gui.IGUI;

public class GUIGroupBuilder implements IGUIGroupBuilder {
	
	private List<IGUI> guis = new ArrayList<IGUI>();

	@Override
	public IGUIGroupBuilder setGUI(IGUI gui) {
		this.guis.add(gui);
		return this;
	}

	@Override
	public IGUIGroup build(String name) {
		if(guis.isEmpty()) {
			return new GUIGroup(name);
		} else {
			return new GUIGroup(name, this.guis);
		}
	}

}
