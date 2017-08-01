package object.gui.group.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import object.gui.component.GUIComponentManagerInterface;
import object.gui.group.GUIGroup;
import object.gui.group.GUIGroupInterface;
import object.gui.gui.GUIInterface;

public class GUIGroupManager implements GUIGroupManagerInterface {

	Map<String, GUIGroupInterface> groups = new HashMap<String, GUIGroupInterface>();
	GUIComponentManagerInterface componentManager;
	
	public GUIGroupManager(GUIComponentManagerInterface componentManager) {
		this.componentManager = componentManager;
	}
	
	@Override
	public GUIGroupInterface createEmpty(String name) {
		GUIGroupInterface group = new GUIGroup(name);
		this.groups.put(group.getName(), group);
		return group;
	}

	@Override
	public GUIGroupInterface get(String name) {
		return groups.get(name);
	}

	@Override
	public void addAll(Collection<GUIGroupInterface> groupList) {
		groupList.forEach(group -> 
		this.groups.put(group.getName(), group));
	}

	@Override
	public void add(GUIGroupInterface group) {
		this.groups.put(group.getName(), group);
	}

	@Override
	public Collection<GUIGroupInterface> getAll() {
		return this.groups.values();
	}

	@Override
	public boolean delete(String name) {
		if (this.groups.containsKey(name)) {
			for (GUIInterface gui : this.groups.get(name).getAll()) {
				gui.getTexts().forEach(text -> 
				this.componentManager.getTexts()
					.delete(text.getName()));
			}
			this.groups.get(name).clean();
			this.groups.remove(name);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void clean() {
		this.groups.values().forEach(group -> group.clean());
		this.groups.clear();		
	}
}
