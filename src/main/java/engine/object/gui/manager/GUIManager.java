package object.gui.manager;

import core.debug.EngineDebug;
import object.gui.component.GUIComponentManager;
import object.gui.component.IGUIComponentManager;
import object.gui.group.manager.GUIGroupManager;
import object.gui.group.manager.IGUIGroupManager;
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
		this.menuSystem = new GUIMenuSystem();
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
