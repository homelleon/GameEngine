package object.gui.group.builder;

import object.gui.group.IGUIGroup;
import object.gui.gui.IGUI;

public interface IGUIGroupBuilder {
	
	IGUIGroupBuilder setGUI(IGUI gui);
	IGUIGroup getGUIGroup(String name);

}
