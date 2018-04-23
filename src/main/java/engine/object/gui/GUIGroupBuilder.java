package object.gui;

import java.util.ArrayList;
import java.util.List;

public class GUIGroupBuilder {
	
	private List<GUI> guis = new ArrayList<GUI>();

	public GUIGroupBuilder setGUI(GUI gui) {
		guis.add(gui);
		return this;
	}

	public GUIGroup build(String name) {
		return guis.isEmpty() ?	new GUIGroup(name) : new GUIGroup(name, guis);
	}

}
