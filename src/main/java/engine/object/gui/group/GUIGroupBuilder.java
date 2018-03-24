package object.gui.group;

import java.util.ArrayList;
import java.util.List;

import object.gui.gui.GUI;

public class GUIGroupBuilder {
	
	private List<GUI> guis = new ArrayList<GUI>();

	public GUIGroupBuilder setGUI(GUI gui) {
		this.guis.add(gui);
		return this;
	}

	public GUIGroup build(String name) {
		if(guis.isEmpty()) {
			return new GUIGroup(name);
		} else {
			return new GUIGroup(name, this.guis);
		}
	}

}
