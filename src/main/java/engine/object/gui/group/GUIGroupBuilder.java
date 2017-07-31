package object.gui.group;

import java.util.ArrayList;
import java.util.List;

import object.gui.gui.GUIInterface;

public class GUIGroupBuilder implements GUIGroupBuilderInterface {
	
	private List<GUIInterface> guis = new ArrayList<GUIInterface>();

	@Override
	public GUIGroupBuilderInterface setGUI(GUIInterface gui) {
		this.guis.add(gui);
		return this;
	}

	@Override
	public GUIGroupInterface getGUIGroup(String name) {
		if(guis.isEmpty()) {
			return new GUIGroup(name);
		} else {
			return new GUIGroup(name, this.guis);
		}
	}

}
