package manager.gui;

import core.debug.EngineDebug;
import manager.gui.component.GUIComponentManager;
import manager.gui.component.IGUIComponentManager;
import manager.gui.group.GUIGroupManager;
import manager.gui.group.IGUIGroupManager;
import object.gui.system.GUIMenuSystem;
import object.gui.system.IGUIMenuSystem;

/**
 * Main graphic user interface manager that includes texture and text managers.
 * 
 * @author homelleon
 *
 */
public class GUIManager implements IGUIManager {

	private static final String GUI_FILE_NAME = "interface";

	private IGUIComponentManager componentManager;
	private IGUIMenuSystem menuSystem;
	private IGUIGroupManager groupManager;
	

	@Override
	public void initialize() {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("GUI Manager");
			EngineDebug.print("Loading User Interface...");
		}
		this.componentManager = new GUIComponentManager(GUI_FILE_NAME);
		this.menuSystem = GUIMenuSystem.getInstace();
		this.groupManager = new GUIGroupManager(this.componentManager);
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.print("Loading complete!");
			EngineDebug.printClose("GUI Manager");
			EngineDebug.printBorder();
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
		if(this.menuSystem != null) {
			this.menuSystem.clean();
		}
		if(this.groupManager != null) {
			this.groupManager.clean();
		}
	}

}
