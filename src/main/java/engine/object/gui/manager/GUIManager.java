package object.gui.manager;

import core.debug.EngineDebug;
import object.gui.component.GUIComponentManager;
import object.gui.component.GUIComponentManagerInterface;
import object.gui.group.manager.GUIGroupManager;
import object.gui.group.manager.GUIGroupManagerInterface;
import object.gui.pattern.menu.system.GUIMenuSystem;
import object.gui.pattern.menu.system.GUIMenuSystemInterface;

public class GUIManager implements GUIManagerInterface {

	private static final String GUI_FILE_NAME = "interface";
	private static final String GUI_MENU_NAME = "menu";

	GUIComponentManagerInterface componentManager;
	GUIMenuSystemInterface menuSystem;
	GUIGroupManagerInterface groupManager;
	

	@Override
	public void initialize() {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Prepare User Interface...");
		}
		this.componentManager = new GUIComponentManager(GUI_FILE_NAME);
		this.menuSystem = new GUIMenuSystem();
		this.groupManager = new GUIGroupManager(this.componentManager);
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("done!");
		}
	}
	
	@Override
	public GUIGroupManagerInterface getGroups() {
		return groupManager;
	}
	
	
	@Override
	public GUIMenuSystemInterface getMenus() {
		return this.menuSystem;
	}


	@Override
	public GUIComponentManagerInterface getComponent() {
		return this.componentManager;
	}

	@Override
	public void render() {
		this.componentManager.render(this.getGroups().getAll());
	}

	@Override
	public void cleanAll() {
		this.menuSystem.clean();
		this.groupManager.clean();
	}

}
