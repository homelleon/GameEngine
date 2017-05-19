package gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import renderEngine.Loader;

public class GUIManager implements GUIManagerInterface {
	
	private static final String TXT_FILE_NAME = "GUITexts";
	private static final String TEXTURE_FILE_NAME = "GUITextures";
	
	GUIComponentManagerInterface componentManager;
	Map<String, GUIGroupInterface> groups = new HashMap<String, GUIGroupInterface>();	
	
	public void init(Loader loader) {
		System.out.println("Prepare User Interface...");
		this.componentManager = new GUIComponentManager(TEXTURE_FILE_NAME, TXT_FILE_NAME, loader);
		System.out.println("Succed!");
	}

	@Override
	public GUIGroupInterface getUIGroup(String name) {
		return groups.get(name);
	}
	
	@Override
	public void addUIGroup(Collection<GUIGroupInterface> groupList) {
		for(GUIGroupInterface group : groupList) {
			this.groups.put(group.getName(), group);
		}		
	}
	
	@Override
	public GUIComponentManagerInterface getComponentManager() {
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
