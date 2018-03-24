package manager.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.EngineDebug;
import object.gui.group.GUIGroup;
import object.gui.gui.GUI;

/**
 * Manages GUIGroups.
 * 
 * @author homelleon
 * 
 * @see IGUIGroupManager
 * @see GUIGroup
 * @see GUI
 */
public class GUIGroupManager {

	Map<String, GUIGroup> groups = new HashMap<String, GUIGroup>();
	GUIComponentManager componentManager;
	
	/**
	 * Contructes group manager with defined component manager.
	 * 
	 * @param componentManager {@link IGUIComponentManagaer} object
	 */
	public GUIGroupManager(GUIComponentManager componentManager) {
		this.componentManager = componentManager;
	}
	
	public GUIGroup createEmpty(String name) {
		GUIGroup group = new GUIGroup(name);
		this.groups.put(group.getName(), group);
		return group;
	}

	public GUIGroup get(String name) {
		return groups.get(name);
	}

	public void addAll(Collection<GUIGroup> groupList) {
		if(groupList!=null && !groupList.isEmpty()) {
			this.groups.putAll(
					groupList.stream()
						.collect(Collectors.toMap(
								GUIGroup::getName, Function.identity())));
		} else {
			if(EngineDebug.hasDebugPermission()) {
				System.err.println(
						"Trying to add null collection value into GUIGroupManager array!");
			}
		}
	}

	public void add(GUIGroup group) {
		this.groups.put(group.getName(), group);
	}

	public Collection<GUIGroup> getAll() {
		return this.groups.values();
	}

	public boolean delete(String name) {
		if (this.groups.containsKey(name)) {
			this.get(name).getAll().stream()
				.flatMap(gui -> gui.getTexts().stream())
				.forEach(text -> this.componentManager.getTexts().delete(text.getName()));
			this.get(name).getAll().stream()
				.flatMap(gui -> gui.getTextures().stream())
				.forEach(texture -> this.componentManager.getTextures().delete(texture.getName()));
			this.groups.get(name).clean();
			this.groups.remove(name);
			return true;
		} else {
			return false;
		}
	}

	public void clean() {
		this.groups.values()
			.forEach(group -> group.clean());
		this.groups.clear();		
	}
}
