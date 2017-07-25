package object.gui.control.menu;

import object.gui.control.object.GUIObject;

public interface GUIMenuInterface extends GUIObject {
	
	void add(GUIObject guiObject);
	GUIObject get(String name);
	void clean();
}
