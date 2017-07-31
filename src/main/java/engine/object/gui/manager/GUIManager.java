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

public class GUIManager implements GUIManagerInterface {

	private static final String GUI_FILE_NAME = "Interface";

	GUIComponentManagerInterface componentManager;
	Map<String, GUIGroupInterface> groups = new HashMap<String, GUIGroupInterface>();

	@Override
	public void initialize() {
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("Prepare User Interface...");
		}
		this.componentManager = new GUIComponentManager(GUI_FILE_NAME);
		if (EngineDebug.hasDebugPermission()) {
			System.out.println("done!");
		}
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
		if (this.groups.containsKey(name)) {
			isExist = true;
			for (GUIInterface gui : this.groups.get(name).getAll()) {
				for (GUIText text : gui.getTexts()) {
					this.componentManager.getTexts().remove(text.getName());
				}
			}
			this.groups.get(name).clean();
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
		this.componentManager.render(this.getAllGUIGroups());
	}

	@Override
	public void cleanAll() {
		for (GUIGroupInterface group : this.groups.values()) {
			group.clean();
		}
		this.groups.clear();
	}

}
