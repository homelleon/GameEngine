package object.gui.group;

import object.gui.gui.IGUI;

public interface IGUIGroupBuilder {
	
	IGUIGroupBuilder setGUI(IGUI gui);
	IGUIGroup getGUIGroup(String name);

}
