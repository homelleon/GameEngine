package manager.gui;

import core.debug.EngineDebug;
import manager.gui.component.GUIComponentManager;
import manager.gui.component.IGUIComponentManager;
import manager.gui.group.GUIGroupManager;
import manager.gui.group.IGUIGroupManager;
import object.gui.system.GUIMenuSystem;
import object.gui.system.IGUIMenuSystem;

public class GUIManager implements IGUIManager {

	private static final String GUI_FILE_NAME = "interface";

	IGUIComponentManager componentManager;
	IGUIMenuSystem menuSystem;
	IGUIGroupManager groupManager;
	

	@Override
	public void initialize() {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading User Interface...");
		}
		this.componentManager = new GUIComponentManager(GUI_FILE_NAME);
		this.menuSystem = GUIMenuSystem.getInstace();
		this.groupManager = new GUIGroupManager(this.componentManager);
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Loading complete!");
			System.out.println("-------------------------");
		}
	}
	
	@Override
	public IGUIGroupManager getGroups() {
		return groupManager;
	}	
	
	@Override
	public IGUIMenuSystem getMenus() {
		return this.menuSystem;
	}

	@Override
	public IGUIComponentManager getComponent() {
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
