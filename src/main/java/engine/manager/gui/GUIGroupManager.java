package manager.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import core.EngineDebug;
import object.gui.GUI;
import object.gui.GUIGroup;

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
		groups.put(group.getName(), group);
		return group;
	}

	public GUIGroup get(String name) {
		return groups.get(name);
	}

	public void addAll(Collection<GUIGroup> groupList) {
		if (groupList == null || groupList.isEmpty()) {
			if (EngineDebug.hasDebugPermission())
				System.err.println("Trying to add null collection value into GUIGroupManager array!");
			return;
		}
		
		groups.putAll(
				groupList.stream()
					.collect(Collectors.toMap(
							GUIGroup::getName, Function.identity())));
	}

	public void add(GUIGroup group) {
		groups.put(group.getName(), group);
	}

	public Collection<GUIGroup> getAll() {
		return groups.values();
	}

	public boolean delete(String name) {
		if (!groups.containsKey(name)) return false;
		
		this.get(name).getAll().stream()
			.flatMap(gui -> gui.getTexts().stream())
			.forEach(text -> this.componentManager.getTexts().delete(text.getName()));
		this.get(name).getAll().stream()
			.flatMap(gui -> gui.getTextures().stream())
			.forEach(texture -> this.componentManager.getTextures().delete(texture.getName()));
		groups.get(name).clean();
		groups.remove(name);
		return true;
	}

	public void clean() {
		groups.values()
			.forEach(group -> group.clean());
		groups.clear();		
	}
}
