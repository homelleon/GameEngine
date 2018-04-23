package manager.gui;

import core.EngineDebug;
import object.gui.element.GUIMenuSystem;

/**
 * Main graphic user interface manager that includes texture and text managers.
 * 
 * @author homelleon
 *
 */
public class GUIManager {

	private static final String GUI_FILE_NAME = "Interface";

	private GUIComponentManager componentManager;
	private GUIMenuSystem menuSystem;
	private GUIGroupManager groupManager;
	
	public void initialize() {
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.printBorder();
			EngineDebug.printOpen("GUI Manager");
			EngineDebug.println("Loading User Interface...");
		}
		
		this.componentManager = new GUIComponentManager(GUI_FILE_NAME);
		this.menuSystem = GUIMenuSystem.getInstace();
		this.groupManager = new GUIGroupManager(this.componentManager);
		
		if (EngineDebug.hasDebugPermission()) {
			EngineDebug.println("Loading complete!");
			EngineDebug.printClose("GUI Manager");
			EngineDebug.printBorder();
		}
	}
	
	public GUIGroupManager getGroups() {
		return groupManager;
	}	
	
	public GUIMenuSystem getMenus() {
		return menuSystem;
	}

	public GUIComponentManager getComponent() {
		return componentManager;
	}

	public void render() {
		componentManager.render(this.getGroups().getAll());
	}

	public void cleanAll() {
		if (menuSystem != null)	menuSystem.clean();		
		if (groupManager != null) groupManager.clean();
	}

}
