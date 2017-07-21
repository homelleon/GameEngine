package object.gui.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.debug.EngineDebug;
import object.gui.component.GUIComponentManager;
import object.gui.component.GUIComponentManagerInterface;
import object.gui.group.GUIGroup;
import object.gui.group.GUIGroupInterface;
import object.gui.gui.GUIInterface;
import object.gui.text.GUIText;
import renderer.loader.Loader;

public class GUIManager implements GUIManagerInterface {
	
	private static final String TXT_FILE_NAME = "GUITexts";
	private static final String TEXTURE_FILE_NAME = "GUITextures";
	
	GUIComponentManagerInterface componentManager;
	Map<String, GUIGroupInterface> groups = new HashMap<String, GUIGroupInterface>();	
	
	public void init(Loader loader) {
		if(EngineDebug.hasDebugPermission()) {
			System.out.println("Prepare User Interface...");
		}
		this.componentManager = new GUIComponentManager(TEXTURE_FILE_NAME, TXT_FILE_NAME);
		
	}
	

	@Override
	public GUIGroupInterface createEmptyGUIGroup(String name) {
		GUIGroupInterface group = new GUIGroup(name);
		this.groups.put(group.getName(), group);
		return group;
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
	public boolean deleteGUIGroup(String name) {
		boolean isExist = false;
		if(this.groups.containsKey(name)) {
			isExist = true;
			for(GUIInterface gui : this.groups.get(name).getAll()) {
				for(GUIText text : gui.getTexts()) {
					this.componentManager.getTexts().remove(text.getName());
				}
			}
			this.groups.get(name).cleanAll();
			this.groups.remove(name);
		}
		return isExist;
	}
	
	@Override
	public GUIComponentManagerInterface getComponent() {
		return this.componentManager;
	}	

	@Override
	public void render() {
		this.componentManager.render();		
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
