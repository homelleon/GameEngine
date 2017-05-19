package userInterfaces;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import renderEngine.Loader;

public class UIManagerBasic implements UIManager {
	
	private static final String TXT_FILE_NAME = "GUITexts";
	private static final String TEXTURE_FILE_NAME = "GUITextures";
	
	UIComponentManager componentManager;
	Map<String, UIGroup> groups = new HashMap<String, UIGroup>();	
	
	public void init(Loader loader) {
		System.out.println("Prepare User Interface...");
		this.componentManager = new UIComponentManagerBasic(TEXTURE_FILE_NAME, TXT_FILE_NAME, loader);
		System.out.println("Succed!");
	}

	@Override
	public UIGroup getUIGroup(String name) {
		return groups.get(name);
	}
	
	@Override
	public void addUIGroup(Collection<UIGroup> groupList) {
		for(UIGroup group : groupList) {
			this.groups.put(group.getName(), group);
		}		
	}
	
	@Override
	public UIComponentManager getComponentManager() {
		return this.componentManager;
	}

	@Override
	public void cleanAll() {
		for(UIGroup group : this.groups.values()) {
			group.hideAll();
			group.cleanAll();
		}
		this.groups.clear();
	}		

}
