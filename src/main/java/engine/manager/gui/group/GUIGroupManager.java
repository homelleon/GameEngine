package manager.gui.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import manager.gui.component.IGUIComponentManager;
import object.gui.group.GUIGroup;
import object.gui.group.IGUIGroup;
import object.gui.gui.IGUI;

public class GUIGroupManager implements IGUIGroupManager {

	Map<String, IGUIGroup> groups = new HashMap<String, IGUIGroup>();
	IGUIComponentManager componentManager;
	
	public GUIGroupManager(IGUIComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	@Override
	public IGUIGroup createEmpty(String name) {
		IGUIGroup group = new GUIGroup(name);
		this.groups.put(group.getName(), group);
		return group;
	}

	@Override
	public IGUIGroup get(String name) {
		return groups.get(name);
	}

	@Override
	public void addAll(Collection<IGUIGroup> groupList) {
		groupList.forEach(group -> 
		this.groups.put(group.getName(), group));
	}

	@Override
	public void add(IGUIGroup group) {
		this.groups.put(group.getName(), group);
	}

	@Override
	public Collection<IGUIGroup> getAll() {
		return this.groups.values();
	}

	@Override
	public boolean delete(String name) {
		if (this.groups.containsKey(name)) {
			for (IGUI gui : this.groups.get(name).getAll()) {
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
