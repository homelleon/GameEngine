package objects.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import engineMain.debug.EngineDebug;
import renderEngine.Loader;

public class GUIManager implements GUIManagerInterface {
	
	private static final String TXT_FILE_NAME = "GUITexts";
	private static final String TEXTURE_FILE_NAME = "GUITextures";
	
	GUIComponentManagerInterface componentManager;
	Map<String, GUIGroupInterface> groups = new HashMap<String, GUIGroupInterface>();	
	
	public void init(Loader loader) {
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Prepare User Interface...");
		}
		this.componentManager = new GUIComponentManager(TEXTURE_FILE_NAME, TXT_FILE_NAME, loader);		
	}

	@Override
	public GUIGroupInterface getGUIGroup(String name) {
		return groups.get(name);
	}
	
	@Override
	public void addAllGUIGroups(Collection<GUIGroupInterface> groupList) {
		groupList.forEach(group -> this.groups.put(group.getName(), group));
	}
	
	@Override
	public void addGUIGroup(GUIGroupInterface group) {
		this.groups.put(group.getName(), group);
	}
	
	@Override
	public Collection<GUIGroupInterface> getAllGUIGroups() {
		return this.groups.values();
	}	
	
	@Override
	public GUIComponentManagerInterface getComponent() {
		return this.componentManager;
	}

	@Override
	public void cleanAll() {
		for(GUIGroupInterface group : this.groups.values()) {
			group.hideAll();
			group.cleanAll();
		}
		this.groups.clear();
	}	

}
