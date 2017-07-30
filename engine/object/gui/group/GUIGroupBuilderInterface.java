package object.gui.group;

import object.gui.gui.GUIInterface;

public interface GUIGroupBuilderInterface {
	
	GUIGroupBuilderInterface setGUI(GUIInterface gui);
	GUIGroupInterface getGUIGroup(String name);

}
